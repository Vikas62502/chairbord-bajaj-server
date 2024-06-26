package com.chairboard.dto.customer.auth

import com.chairboard.client.HttpClient
import com.chairboard.dto.customer.BaseDTO
import com.chairboard.utils.getRequestTime
import kotlinx.serialization.Serializable

@Serializable
data class ValidateOtpClientRequest(
    val otp: String,
    val sessionId: String,
) {
    fun toServerRequest(agentId: String) = ValidateOtpRequest(
        validateOtpReq = ValidateOtpReq(
            otp = otp,
            requestId = System.currentTimeMillis().toString(),
            sessionId = sessionId,
            channel = HttpClient.CHANNEL,
            agentId = agentId,
            reqDateTime = getRequestTime(),
        )
    )
}

@Serializable
data class ValidateOtpRequest(
    val validateOtpReq: ValidateOtpReq,
) : BaseDTO

@Serializable
data class ValidateOtpReq(
    val otp: String,
    val requestId: String,
    val sessionId: String,
    val channel: String,
    val agentId: String,
    val reqDateTime: String,
)
