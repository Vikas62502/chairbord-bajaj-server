package com.chairboard.dto.customer.vehicle

import com.chairboard.client.HttpClient
import com.chairboard.dto.customer.BaseDTO
import com.chairboard.utils.getRequestTime
import kotlinx.serialization.Serializable

@Serializable
data class VehicleMakerServerRequest(
    val getVehicleMake: GetVehicleMake,
) : BaseDTO {
    companion object {
        fun buildRequest(agentId: String, sessionId: String): VehicleMakerServerRequest {
            return VehicleMakerServerRequest(
                getVehicleMake = GetVehicleMake(
                    requestId = System.currentTimeMillis().toString(),
                    sessionId = sessionId,
                    channel = HttpClient.CHANNEL,
                    agentId = agentId,
                    reqDateTime = getRequestTime()
                )
            )
        }
    }
}

@Serializable
data class GetVehicleMake(
    val requestId: String,
    val sessionId: String,
    val channel: String,
    val agentId: String,
    val reqDateTime: String,
)