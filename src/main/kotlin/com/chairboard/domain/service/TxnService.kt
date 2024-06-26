package com.chairboard.domain.service

import com.chairboard.domain.repository.TxnRepository
import com.chairboard.domain.repository.UserRepository
import com.chairboard.dto.customer.txn.TxnRequest
import com.chairboard.dto.customer.txn.TxnRowResponse
import com.chairboard.utils.TXN_CREDIT
import org.jetbrains.exposed.sql.Expression

class TxnService(
    private val userRepository: UserRepository,
    private val txnRepository: TxnRepository) : Service {

    fun insertRow(reference: String, input: TxnRequest): Int {
        val agent = userRepository.getById(input.userId)
        val currentBalance = agent?.walletBalance ?: 0
        val newBalance = if (input.txnType == TXN_CREDIT) currentBalance + input.txnAmount else currentBalance - input.txnAmount
        val updatedAgent = userRepository.updateWalletBalance(input.userId, newBalance)
        txnRepository.insert(input.toRow(newBalance, reference, if (input.txnType == TXN_CREDIT) "Money Credit" else "Money Debit" ), updatedAgent!!)
        return newBalance
    }

    fun getAll(): List<TxnRowResponse> {
        return txnRepository.getAll()
    }
    fun getAllForAgentByType(agentId: String, txnType: String): List<TxnRowResponse> {
        return txnRepository.getAllForAgentIdByType(agentId, txnType)
    }
}