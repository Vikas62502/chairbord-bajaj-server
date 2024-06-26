package com.chairboard.dto.customer.auth

import com.chairboard.dto.ClientResponse
import kotlinx.serialization.Serializable

@Serializable
data class ValidateOtpResponse(
    val response: ClientResponse,
    val validateOtpResp: ValidateOtpResp,
)

@Serializable
data class ValidateOtpResp(
    val vrnDetails: VrnDetails,
    val npciStatus: List<NpciStatu>,
    val custDetails: CustDetails,
    val respDateTime: String,
    val requestId: String,
    val reqType: String,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
)

@Serializable
data class VrnDetails(
    val vehicleNo: String,
    val chassisNo: String,
    val engineNo: String,
    val vehicleManuf: String,
    val model: String,
    val vehicleColour: String,
    val type: String,
    val rtoStatus: String,
    val isCommercial: Boolean,
    val tagVehicleClassId: String,
    val npciVehicleClassId: String,
    val vehicleType: String,
    val rechargeAmount: String,
    val securityDeposit: String,
    val tagCost: String,
    val repTagCost: String,
)

@Serializable
data class NpciStatu(
    val mapperTagStatus: String,
    val issuerName: String,
)

@Serializable
data class CustDetails(
    val name: String,
    val walletStatus: String,
    val kycStatus: String,
    val walletId: String,
    val mobileNo: String,
)
