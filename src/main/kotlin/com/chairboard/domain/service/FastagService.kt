package com.chairboard.domain.service

import com.chairboard.domain.exceptions.UserInactiveException
import com.chairboard.domain.repository.FastagRepository
import com.chairboard.domain.repository.UserRepository
import com.chairboard.dto.customer.fastag.*
import com.chairboard.dto.customer.txn.TxnRequest
import com.chairboard.plugins.Modules.customerRepository
import com.chairboard.plugins.Modules.txnRepository
import com.chairboard.utils.TXN_DEBIT
import kotlin.math.roundToInt

class FastagService(
    private val userRepository: UserRepository,
    private val fastagRepository: FastagRepository) : Service {

    fun insertRow(userId: String, clientRequest: FastagRegisterClientRequest, responseData: FastagRegisterResponse?) {
        val agent = userRepository.getById(userId)
        val debitAmount = clientRequest.vrnDetails.debitAmt.toDoubleOrNull()?.roundToInt() ?: 0
        val newBalance = agent?.walletBalance?.minus(debitAmount)!!
        val txnRequest = TxnRequest(
            userId,
            txnType = TXN_DEBIT,
            mobile = agent.mobile,
            txnAmount = debitAmount,
            reference = clientRequest.vrnDetails.vrn,
            session = clientRequest.sessionId,
            remarks = "Tag Issuance",
        )
        val updatedAgent = userRepository.updateWalletBalance(userId, newBalance)
        txnRepository.insert(txnRequest.toRow(newBalance, agent.mobile, "Tag Issuance"), updatedAgent!!)

        agent.let {
            if (it.isActive) {
                val fastagRow = FastagRow(
                    session = clientRequest.sessionId,
                    request_id = clientRequest.sessionId,
                    vehicle_no = clientRequest.vrnDetails.vrn,
                    tag_sr_no = clientRequest.fasTagDetails.serialNo,
                    status = "Pending",
                    chassis = clientRequest.vrnDetails.chassis,
                    engine = clientRequest.vrnDetails.engine,
                    vehicleManuf = clientRequest.vrnDetails.vehicleManuf,
                    vehicleImage = clientRequest.fasTagDetails.vehicleImage,
                    npciStatus = responseData?.tagRegistrationResp?.npciStatus ?:"",
                    type = clientRequest.vrnDetails.type,
                    name = clientRequest.custDetails.name,
                    walletId = clientRequest.custDetails.walletId,
                    tagCost = clientRequest.vrnDetails.tagCost.toDoubleOrNull() ?: 0.0,
                    securityDeposit = clientRequest.vrnDetails.securityDeposit.toDoubleOrNull() ?: 0.0,
                    rechargeAmount = clientRequest.vrnDetails.rechargeAmount.toDoubleOrNull() ?: 0.0,
                    vehicleColour = clientRequest.vrnDetails.vehicleColour,
                    vehicleType = clientRequest.vrnDetails.vehicleType,
                    tagVehicleClassId = clientRequest.vrnDetails.tagVehicleClassId,
                    npciVehicleClassId = clientRequest.vrnDetails.npciVehicleClassId,
                    isCommercial = clientRequest.vrnDetails.isCommercial,
                    mobileNo = clientRequest.custDetails.mobileNo,
                    tid = clientRequest.fasTagDetails.tid,
                    recordType = "Tag Registration",
                    rcImageFront = clientRequest.fasTagDetails.rcImageFront,
                    rcImageBack = clientRequest.fasTagDetails.rcImageBack,
                    model = clientRequest.vrnDetails.model,
                    debitAmt = clientRequest.vrnDetails.debitAmt.toDoubleOrNull() ?: 0.0,
                    udf1 = clientRequest.fasTagDetails.udf1,
                    udf2 = clientRequest.fasTagDetails.udf2,
                    udf3 = clientRequest.fasTagDetails.udf3,
                    udf4 = clientRequest.fasTagDetails.udf4,
                    udf5 = clientRequest.fasTagDetails.udf5,
                )
                fastagRepository.insert(sessionId = clientRequest.sessionId, input = fastagRow, agent)
            } else {
                throw UserInactiveException()
            }
        }
    }

    fun insertRow(userId: String, clientRequest: FastagReplaceClientRequest, responseData: FastagRegisterResponse?) {
        val agent = userRepository.getById(userId)
        val debitAmount = (clientRequest.debitAmt.toDoubleOrNull()?.roundToInt() ?: 0)
        val newBalance = agent?.walletBalance?.minus(debitAmount) ?:0
        val txnRequest = TxnRequest(
            userId,
            txnType = TXN_DEBIT,
            mobile = agent?.mobile!!,
            txnAmount = debitAmount,
            reference = clientRequest.vehicleNo,
            remarks = "Tag Replacement",
            session = clientRequest.sessionId,
        )
        val updatedAgent = userRepository.updateWalletBalance(userId, newBalance)
        txnRepository.insert(txnRequest.toRow(newBalance, agent.mobile, "Tag Replacement"), updatedAgent!!)

        agent.let {
            if (it.isActive) {
                val fastagRow = FastagRow(
                    session = clientRequest.sessionId,
                    request_id = clientRequest.sessionId,
                    vehicle_no = clientRequest.vehicleNo,
                    tag_sr_no = clientRequest.serialNo,
                    status = "Pending",
                    chassis = clientRequest.chassisNo,
                    engine = "",
                    vehicleManuf = "",
                    vehicleImage = "",
                    npciStatus = responseData?.tagRegistrationResp?.npciStatus ?: "",
                    type = "",
                    name = "",
                    walletId = clientRequest.walletId,
                    tagCost = 0.0,
                    securityDeposit = 0.0,
                    rechargeAmount = 0.0,
                    vehicleColour = "",
                    vehicleType = "",
                    tagVehicleClassId = "",
                    npciVehicleClassId = "",
                    isCommercial = false,
                    mobileNo = clientRequest.mobileNo,
                    tid = "",
                    recordType = "Tag Replacement",
                    rcImageFront = "",
                    rcImageBack = "",
                    model = "",
                    debitAmt = clientRequest.debitAmt.toDoubleOrNull() ?: 0.0,
                    udf1 = clientRequest.udf1,
                    udf2 = clientRequest.udf2,
                    udf3 = clientRequest.udf3,
                    udf4 = clientRequest.udf4,
                    udf5 = clientRequest.udf5,
                )
                fastagRepository.insert(sessionId = clientRequest.sessionId, input = fastagRow, agent)
            } else {
                throw UserInactiveException()
            }
        }
    }

    fun updateStatus(id: String, status: String) {
        return fastagRepository.updateStatus(id, status)
    }

    fun getAllFastags(status: String, agentId: String): List<FastagRowListItem> {
        return if (agentId.isNotBlank()) {
            fastagRepository.getAllFastagsForAgent(agentId, status)
        } else {
            fastagRepository.getAllFastags(status)
        }
    }

    fun getFastagDetail(id: String): FastagRow? {
            return fastagRepository.getFastagDetail(id)
    }

}