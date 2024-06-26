package com.chairboard.routes

import com.chairboard.client.HttpClient
import com.chairboard.client.HttpClient.BASE_ENDPOINT
import com.chairboard.domain.service.CustomerService
import com.chairboard.domain.service.DocumentService
import com.chairboard.dto.Result
import com.chairboard.dto.customer.wallet.CreateWalletClientRequest
import com.chairboard.dto.customer.wallet.CreateWalletResponse
import com.chairboard.plugins.AuthUser
import com.chairboard.utils.AesEncryption.decrypt
import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute() {
    get("/profile") {
        val agent = call.authentication.principal<AuthUser>()
        call.respond(
            status = HttpStatusCode.OK,
            message = Result.Success(data = agent?.toResponse(), success = true)
        )
    }
}

fun Route.customerRoute(customerService: CustomerService, documentService: DocumentService) {
    createCustomer(customerService, documentService)
}

fun Route.createCustomer(customerService: CustomerService, documentService: DocumentService) {
    val client = HttpClient.instance
    post("/create-customer") {
        val agent = call.authentication.principal<AuthUser>()
        val request = call.receive<CreateWalletClientRequest>()
        val serverRequest = request.toServerRequest(agent?.associatedAgentId ?: "")
        val response = client.post("$BASE_ENDPOINT/createCustomer") {
            setBody(serverRequest.encrypt())
        }
        val res = response.body<String>().replaceIndent("").replace("\n", "").trim()
        if (res.contains(" ")) {
            call.respond(status = response.status, message = Result.Failure(error = res))
        } else {
            val data = decrypt(res)
            val responseData = Gson().fromJson(data, CreateWalletResponse::class.java)
            val isSuccess = responseData.response.code == "00"
            if (isSuccess) {
                val customerId = customerService.insertRow(agent!!.id, request, responseData)
                documentService.insertRow(customerId, request)
            }
            call.respond(
                status = response.status,
                message = Result.Success(data = responseData, success = isSuccess)
            )
        }
    }
}
