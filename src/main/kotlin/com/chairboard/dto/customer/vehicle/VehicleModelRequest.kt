package com.chairboard.dto.customer.vehicle

import com.chairboard.client.HttpClient
import com.chairboard.dto.customer.BaseDTO
import com.chairboard.utils.getRequestTime
import kotlinx.serialization.Serializable

@Serializable
data class VehicleModelClientRequest(
    val sessionId: String,
    val vehicleMake: String,
) {
    fun toServerRequest(agentId: String) = VehicleModelRequest(
        getVehicleModel = GetVehicleModel(
            agentId = agentId,
            sessionId = sessionId,
            channel = HttpClient.CHANNEL,
            requestId = System.currentTimeMillis().toString(),
            reqDateTime = getRequestTime(),
            vehicleMake = vehicleMake
        )
    ).encrypt()
}

@Serializable
data class VehicleModelRequest(
    val getVehicleModel: GetVehicleModel
) : BaseDTO

@Serializable
data class GetVehicleModel(
    val agentId: String,
    val channel: String,
    val reqDateTime: String,
    val requestId: String,
    val sessionId: String,
    val vehicleMake: String
)