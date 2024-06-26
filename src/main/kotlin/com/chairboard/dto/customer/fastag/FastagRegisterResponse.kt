package com.chairboard.dto.customer.fastag

import com.chairboard.dto.ClientResponse
import kotlinx.serialization.Serializable

@Serializable
data class FastagRegisterResponse(
    val response: ClientResponse,
    val tagRegistrationResp: TagRegistrationResp,
)

@Serializable
data class TagRegistrationResp(
    val vrn: String,
    val npciStatus: String,
    val walletId: String,
    val tid: String,
    val serialNo: String,
    val agentBalance: String,
    val channel: String,
    val agentId: String,
    val requestId: String,
    val respDateTime: String,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
)
