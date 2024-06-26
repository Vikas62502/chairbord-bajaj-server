package com.chairboard.dto.customer.wallet

import com.chairboard.client.HttpClient
import com.chairboard.dto.customer.BaseDTO
import com.chairboard.utils.getRequestTime
import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletClientRequest(
    val sessionId: String,
    val name: String,
    val lastName: String,
    val mobileNo: String,
    val dob: String,
    val doc: List<Doc>,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
) {
    fun toServerRequest(agentId: String) = CreateWalletRequest(
        reqWallet = ReqWallet(
            requestId = System.currentTimeMillis().toString(),
            sessionId = sessionId,
            channel = HttpClient.CHANNEL,
            agentId = agentId,
            reqDateTime = getRequestTime()
        ),
        custDetails = CustDetails(
            name = name,
            lastName= lastName,
            mobileNo = mobileNo,
            dob, doc, udf1, udf2, udf3, udf4, udf5
        )
    )
}

@Serializable
data class CreateWalletRequest(
    val reqWallet: ReqWallet,
    val custDetails: CustDetails,
) : BaseDTO

@Serializable
data class ReqWallet(
    val requestId: String,
    val sessionId: String,
    val channel: String,
    val agentId: String,
    val reqDateTime: String,
)

@Serializable
data class CustDetails(
    val name: String,
    val lastName: String,
    val mobileNo: String,
    val dob: String,
    val doc: List<Doc>,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
)

@Serializable
data class Doc(
    val docType: Int,
    val docNo: String,
    val expiryDate: String,
)
