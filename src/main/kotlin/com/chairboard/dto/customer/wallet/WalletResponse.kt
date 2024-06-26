package com.chairboard.dto.customer.wallet

import com.chairboard.dto.ClientResponse
import kotlinx.serialization.Serializable


@Serializable
data class CreateWalletResponse(
    val response: ClientResponse,
    val custDetails: CustDetailsResponse?,
)

@Serializable
data class CustDetailsResponse(
    val name: String,
    val lastName: String,
    val walletStatus: String?,
    val kycStatus: String?,
    val mobileNo: String,
    val walletId: String?,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
    val respDateTime: String,
)
