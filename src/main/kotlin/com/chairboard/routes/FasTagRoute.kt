package com.chairboard.routes

import com.chairboard.client.HttpClient
import com.chairboard.client.HttpClient.BASE_ENDPOINT
import com.chairboard.domain.exceptions.InsufficientBalanceException
import com.chairboard.domain.service.FastagService
import com.chairboard.domain.service.UserService
import com.chairboard.dto.Result
import com.chairboard.dto.customer.fastag.FastagRegisterClientRequest
import com.chairboard.dto.customer.fastag.FastagRegisterResponse
import com.chairboard.dto.customer.fastag.FastagReplaceClientRequest
import com.chairboard.dto.decrypt
import com.chairboard.plugins.AuthUser
import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.roundToInt

fun Route.fastagRoute(userService: UserService, fastagService: FastagService) {
    registerFastag(fastagService, userService)
    replaceFastag(fastagService, userService)
}

fun Route.registerFastag(fastagService: FastagService, userService: UserService) {
    val client = HttpClient.instance
    post("/register-fastag") {
        val agent = call.authentication.principal<AuthUser>()

        val clientRequest = call.receive<FastagRegisterClientRequest>()
        if ((agent?.walletBalance!!) < (clientRequest.vrnDetails.debitAmt.toDoubleOrNull()?.roundToInt() ?: 0)) {
            call.respond(status = HttpStatusCode.BadRequest, message = Result.Failure(error = InsufficientBalanceException().toErrorResponse()))
            return@post
        }
        fastagService.insertRow(agent.id, clientRequest, null)
        val response = client.post("$BASE_ENDPOINT/registerFastag") {
            setBody(clientRequest.toServerRequest(agentId = agent.associatedAgentId ?: ""))
        }
        val res = response.body<String>().replaceIndent("").replace("\n", "").trim()
        if (res.contains(" ")) {
            call.respond(status = response.status, message = Result.Failure(error = res))
        } else {
            val responseData = Gson().fromJson(res.decrypt(), FastagRegisterResponse::class.java)

            if (responseData.response.code == "00") {
                fastagService.insertRow(agent.id, clientRequest, responseData)
            }

            call.respond(status = response.status, message = Result.Success(data = responseData, success = responseData.response.code == "00"))
        }
    }
}

fun Route.replaceFastag(fastagService: FastagService, userService: UserService) {
    val client = HttpClient.instance
    post("/replace-fastag") {
        val agent = call.authentication.principal<AuthUser>()
        val clientRequest = call.receive<FastagReplaceClientRequest>()
        if ((agent?.walletBalance!!) < (clientRequest.debitAmt.toDoubleOrNull()?.roundToInt() ?: 0)) {
            call.respond(status = HttpStatusCode.BadRequest, message = Result.Failure(error = InsufficientBalanceException().toErrorResponse()))
            return@post
        }
        val response = client.post("$BASE_ENDPOINT/replaceFastag") {
            setBody(clientRequest.toServerRequest(agentId = agent.associatedAgentId ?: ""))
        }
        fastagService.insertRow(agent.id, clientRequest, null)
        val res = response.body<String>().replaceIndent("").replace("\n", "").trim()
        if (res.contains(" ")) {
            call.respond(status = response.status, message = Result.Failure(error = res))
        } else {
            val responseData = Gson().fromJson(res.decrypt(), FastagRegisterResponse::class.java)
            if (responseData.response.code =="00") {
                fastagService.insertRow(agent.id, clientRequest, responseData)
            }
            call.respond(
                status = response.status,
                message = Result.Success(data = responseData, success = responseData.response.code == "00")
            )
        }
    }
}