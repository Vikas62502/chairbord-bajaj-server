package com.chairboard.dto.customer.fastag

import com.chairboard.client.HttpClient
import com.chairboard.dto.customer.BaseDTO
import com.chairboard.utils.getRequestTime
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FastagRegisterClientRequest(
    val sessionId: String,
    val vrnDetails: VrnDetails,
    val custDetails: CustDetails,
    val fasTagDetails: FasTagDetails,
) {
    fun toServerRequest(agentId: String) = FastagRegisterRequest(
        regDetails = RegDetails(
            requestId = System.currentTimeMillis().toString(),
            sessionId = sessionId,
            channel = HttpClient.CHANNEL,
            agentId = agentId,
            reqDateTime = getRequestTime(),
        ),
        vrnDetails = vrnDetails,
        custDetails = custDetails,
        fasTagDetails = fasTagDetails,
    ).encrypt()
}

@Serializable
data class FastagRegisterRequest(
    val regDetails: RegDetails,
    val vrnDetails: VrnDetails,
    val custDetails: CustDetails,
    val fasTagDetails: FasTagDetails,
) : BaseDTO

@Serializable
data class RegDetails(
    val requestId: String,
    val sessionId: String,
    val channel: String,
    val agentId: String,
    val reqDateTime: String,
)

@Serializable
data class VrnDetails(
    val vrn: String,
    val chassis: String,
    val engine: String,
    val vehicleManuf: String,
    val model: String,
    val vehicleColour: String,
    val type: String,
    val status: String,
    val npciStatus: String,
    val isCommercial: Boolean,
    val tagVehicleClassId: String,
    val npciVehicleClassId: String,
    val vehicleType: String,
    val rechargeAmount: String,
    val securityDeposit: String,
    val tagCost: String,
    val debitAmt: String,
)

@Serializable
data class CustDetails(
    val name: String,
    val mobileNo: String,
    val walletId: String,
)

@Serializable
data class FasTagDetails(
    val serialNo: String,
    val tid: String,
    val rcImageFront: String,
    val rcImageBack: String,
    val vehicleImage: String,
    val udf1: String,
    val udf2: String,
    val udf3: String,
    val udf4: String,
    val udf5: String,
)
