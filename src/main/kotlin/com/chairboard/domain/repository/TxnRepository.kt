package com.chairboard.domain.repository

import com.chairboard.database.entity.Transaction
import com.chairboard.database.entity.User
import com.chairboard.database.tables.TransactionTable
import com.chairboard.dto.customer.txn.TxnRequest
import com.chairboard.dto.customer.txn.TxnRow
import com.chairboard.dto.customer.txn.TxnRowResponse
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TxnRepository : Repository {

    fun insert(input: TxnRow, agent: User): Transaction {
        return transaction {
            return@transaction Transaction.new(UUID.randomUUID().toString()) {
                txnType = input.txnType
                user = agent
                balance = input.balance
                mobile = input.mobile
                txnAmount = input.txnAmount
                reference = input.reference
                txnSource  = input.txnSource
                remarks = input.remarks
                session = input.session
            }
        }
    }

    fun getAll(): List<TxnRowResponse> {
        return transaction {
            return@transaction Transaction.all().orderBy(TransactionTable.created_at to SortOrder.DESC).map {
                TxnRowResponse(
                    agent = it.user.toResponse(),
                    txnType = it.txnType,
                    mobile = it.mobile,
                    balance = it.balance,
                    txnAmount = it.txnAmount,
                    reference = it.reference,
                    txnSource = it.txnSource,
                    createdAt = it.createdAt.toString(),
                    session = it.session
                )
            }
        }
    }

    private fun getAllForAgentId(agentId: String): List<TxnRowResponse> {
        return transaction {
            return@transaction Transaction.find { TransactionTable.user eq agentId }.orderBy(TransactionTable.created_at to SortOrder.DESC).map {
                TxnRowResponse(
                    agent = it.user.toResponse(),
                    txnType = it.txnType,
                    mobile = it.mobile,
                    balance = it.balance,
                    txnAmount = it.txnAmount,
                    reference = it.reference,
                    txnSource = it.txnSource,
                    createdAt = it.createdAt.toString(),
                    session = it.session
                )
            }
        }
    }

    fun getAllForAgentIdByType(agentId: String, txnType: String): List<TxnRowResponse> {
        if (txnType == "ALL" || txnType.isBlank()) {
            return getAllForAgentId(agentId)
        }
        return transaction {
            return@transaction Transaction.find { TransactionTable.user eq agentId and(TransactionTable.txn_type eq txnType) }.orderBy(TransactionTable.created_at to SortOrder.DESC).map {
                TxnRowResponse(
                    agent = it.user.toResponse(),
                    txnType = it.txnType,
                    mobile = it.mobile,
                    balance = it.balance,
                    txnAmount = it.txnAmount,
                    reference = it.reference,
                    txnSource = it.txnSource,
                    createdAt = it.createdAt.toString(),
                    session = it.session
                )
            }
        }
    }
}