package com.chairboard.dto.customer.auth

import com.chairboard.dto.ClientResponse
import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResponse(
    val response: ClientResponse,
    val validateCustResp: ValidateCustResp,
)

@Serializable
data class ValidateCustResp(
    val requestId: String,
    val sessionId: String,
    val channel: String,
    val agentId: String,
    val vehicleNo: String?,
    val chassisNo: String,
    val mobileNo: String,
    val respDateTime: String,
    val otpStatus: String,
    val reqType: String?,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
)
