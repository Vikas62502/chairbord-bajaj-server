package com.chairboard.dto.customer.txn

import com.chairboard.dto.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class TxnRow(
    val userId: String,
    val txnType: String,
    val mobile: String,
    val balance: Int,
    val txnAmount: Int,
    val reference: String,
    val txnSource: String,
    val remarks: String,
    val session: String?,
)

@Serializable
data class TxnRequest(
    val userId: String,
    val txnType: String,
    val mobile: String,
    val txnAmount: Int,
    val reference: String,
    val remarks: String,
    val session: String?,
) {
    fun toRow(balance: Int, reference: String, source: String) = TxnRow(
        userId = userId,
        txnType = txnType,
        mobile = mobile,
        balance = balance,
        txnAmount = txnAmount,
        reference = reference,
        txnSource = source,
        remarks = remarks,
        session = session,
    )
}

@Serializable
data class AddTransactionResponse(
    val message: String = "Transaction successful",
    val balance: Int
)

@Serializable
data class AllTransactions(
    val transactions: List<TxnRowResponse>
)

@Serializable
data class TxnRowResponse(
    val agent: UserResponse,
    val txnType: String,
    val mobile: String,
    val balance: Int,
    val txnAmount: Int,
    val reference: String,
    val txnSource: String,
    val createdAt: String,
    val session: String?,
)