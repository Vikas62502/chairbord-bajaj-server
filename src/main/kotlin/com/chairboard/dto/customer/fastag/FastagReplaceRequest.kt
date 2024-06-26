package com.chairboard.dto.customer.fastag

import com.chairboard.client.HttpClient
import com.chairboard.dto.customer.BaseDTO
import com.chairboard.utils.getRequestTime
import kotlinx.serialization.Serializable

@Serializable
data class FastagReplaceClientRequest(
    val mobileNo: String,
    val walletId: String,
    val vehicleNo: String,
    val chassisNo: String,
    val debitAmt: String,
    val sessionId: String,
    val serialNo: String,
    val reason: String,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
) {
    fun toServerRequest(agentId: String) = FastagReplaceRequest(
        tagReplaceReq = TagReplaceReq(
            mobileNo = mobileNo,
            walletId = walletId,
            vehicleNo = vehicleNo,
            channel = HttpClient.CHANNEL,
            chassisNo = chassisNo,
            agentId = agentId,
            requestId = System.currentTimeMillis().toString(),
            reqDateTime = getRequestTime(),
            debitAmt = debitAmt,
            sessionId = sessionId,
            serialNo = serialNo,
            reason = reason,
            udf1 = udf1,
            udf2 = udf2,
            udf3 = udf3,
            udf4 = udf4,
            udf5 = udf5,
        )
    ).encrypt()
}

@Serializable
data class FastagReplaceRequest(
    val tagReplaceReq: TagReplaceReq,
) : BaseDTO

@Serializable
data class TagReplaceReq(
    val mobileNo: String,
    val walletId: String,
    val vehicleNo: String,
    val channel: String,
    val chassisNo: String,
    val agentId: String,
    val reqDateTime: String,
    val debitAmt: String,
    val requestId: String,
    val sessionId: String,
    val serialNo: String,
    val reason: String,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
)
