package com.chairboard.dto.customer.auth

import com.chairboard.dto.customer.BaseDTO
import com.chairboard.utils.getRequestTime
import kotlinx.serialization.Serializable

@Serializable
data class SendOTPRequest(val validateCustReq: ValidateCustReq) : BaseDTO

@Serializable
data class ValidateCustReq(
    val requestId: String,
    val mobileNo: String,
    val vehicleNo: String,
    val chassisNo: String,
    val reqType: String,
    val channel: String,
    val agentId: String,
    val reqDateTime: String,
    val resend: String,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
)

@Serializable
data class SendOtpClientRequest(
    val mobileNo: String,
    val vehicleNo: String,
    val chassisNo: String,
    val reqType: String,
    val resend: String,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
) {
    fun toServerRequest(agentId: String, channel: String) = SendOTPRequest(
        validateCustReq = ValidateCustReq(
            requestId = System.currentTimeMillis().toString(),
            mobileNo = mobileNo,
            vehicleNo = vehicleNo,
            chassisNo = chassisNo,
            reqType = reqType,
            channel = channel,
            agentId = agentId,
            reqDateTime = getRequestTime(),
            resend = resend,
            udf1, udf2, udf3, udf4, udf5
        )
    )
}