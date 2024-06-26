package com.chairboard.routes

import com.chairboard.client.HttpClient
import com.chairboard.client.HttpClient.BASE_ENDPOINT
import com.chairboard.domain.service.UserService
import com.chairboard.dto.Result
import com.chairboard.dto.customer.vehicle.VehicleMakerResponse
import com.chairboard.dto.customer.vehicle.VehicleMakerServerRequest
import com.chairboard.dto.customer.vehicle.VehicleModelClientRequest
import com.chairboard.dto.customer.vehicle.VehicleModelResponse
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

fun Route.vehicleRoute(userService: UserService) {
    vehicleMakerList(userService)
    vehicleModelList(userService)
}

fun Route.vehicleMakerList(userService: UserService) {
    val client = HttpClient.instance
    get("/vehicle-makers") {
        val agent = call.authentication.principal<AuthUser>()
        val sessionId = call.request.queryParameters["sessionId"] ?: ""
        val request = VehicleMakerServerRequest.buildRequest(
            agentId = agent?.associatedAgentId ?: "",
            sessionId = sessionId
        )
        val response = client.post("$BASE_ENDPOINT/vehicleMakerList") {
            setBody(request.encrypt())
        }
        val res = response.body<String>().replaceIndent("").replace("\n", "").trim()
        if (res.contains(" ")) {
            call.respond(status = response.status, message = Result.Failure(error = res))
        } else {
            val data = decrypt(res)
            val responseData = Gson().fromJson(data, VehicleMakerResponse::class.java)
            call.respond(
                status = response.status,
                message = Result.Success(data = responseData, success = responseData.response.code == "00")
            )
        }
    }
}

fun Route.vehicleModelList(userService: UserService) {
    val client = HttpClient.instance
    post("/vehicle-models") {
        val agent = call.authentication.principal<AuthUser>()
        val clientRequest = call.receive<VehicleModelClientRequest>()
        val response = client.post("$BASE_ENDPOINT/vehicleModelList") {
            setBody(clientRequest.toServerRequest(agentId = agent?.associatedAgentId ?: ""))
        }
        val res = response.body<String>().replaceIndent("").replace("\n", "").trim()
        if (res.contains(" ")) {
            call.respond(status = response.status, message = Result.Failure(error = res))
        } else {
            val data = decrypt(res)
            val responseData = Gson().fromJson(data, VehicleModelResponse::class.java)
            call.respond(
                status = response.status,
                message = Result.Success(data = responseData, success = responseData.response.code == "00")
            )
        }
    }
}