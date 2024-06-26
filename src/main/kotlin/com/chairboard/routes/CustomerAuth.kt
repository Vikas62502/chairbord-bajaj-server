package com.chairboard.routes

import com.chairboard.client.HttpClient
import com.chairboard.client.HttpClient.BASE_ENDPOINT
import com.chairboard.client.HttpClient.CHANNEL
import com.chairboard.dto.Result
import com.chairboard.dto.customer.auth.SendOtpClientRequest
import com.chairboard.dto.customer.auth.SendOtpResponse
import com.chairboard.dto.customer.auth.ValidateOtpClientRequest
import com.chairboard.dto.customer.auth.ValidateOtpResponse
import com.chairboard.dto.toClientError
import com.chairboard.plugins.AuthUser
import com.chairboard.utils.AesEncryption.decrypt
import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerAuth() {
    val client = HttpClient.instance
    post("/send-otp") {
        val agent = call.authentication.principal<AuthUser>()
        val request = call.receive<SendOtpClientRequest>()
        val serverRequest = request.toServerRequest(agent?.associatedAgentId ?: "", channel = CHANNEL)
        val response = client.post("$BASE_ENDPOINT/sendOtp") {
            setBody(serverRequest.encrypt())
        }
        val responseString = response.body<String>().replaceIndent("").replace("\n", "").trim()
        if (responseString.contains(" ")) {
            call.respond(status = response.status, message = Result.Failure(error = responseString))
        } else {
            val data = decrypt(responseString)
            val responseData = Gson().fromJson(data, SendOtpResponse::class.java)
            call.respond(
                status = response.status,
                message = Result.Success(data = responseData, success = responseData.response.code == "00")
            )
        }
    }

    post("/verify-otp") {
        val agent = call.authentication.principal<AuthUser>()
        val clientRequest = call.receive<ValidateOtpClientRequest>()
        val serverRequest = clientRequest.toServerRequest(agentId = agent?.associatedAgentId ?: "")
        val response = client.post("$BASE_ENDPOINT/validateCustomerDetails") {
            setBody(serverRequest.encrypt())
        }
        val responseString = response.body<String>().replaceIndent("").replace("\n", "").trim()
        if (responseString.contains(" ")) {
            call.respond(status = response.status, message = Result.Failure(error = responseString.toClientError()))
        } else {
            val data = decrypt(responseString)
            val responseData = Gson().fromJson(data, ValidateOtpResponse::class.java)
            call.respond(
                status = response.status,
                message = Result.Success(data = responseData, success = responseData.response.code == "00")
            )
        }
    }
}
