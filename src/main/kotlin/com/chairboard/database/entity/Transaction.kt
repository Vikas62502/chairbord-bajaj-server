package com.chairboard.database.entity

import com.chairboard.database.tables.FastagTable
import com.chairboard.database.tables.UsersTable
import com.chairboard.database.tables.TransactionTable
import com.chairboard.dto.UserResponse
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Transaction(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Transaction>(TransactionTable)
    var user by User referencedOn TransactionTable.user
    var balance by TransactionTable.balance
    var txnType by TransactionTable.txn_type
    var mobile by TransactionTable.mobile
    var txnAmount by TransactionTable.txn_amount
    var reference by TransactionTable.reference
    var txnSource by TransactionTable.txn_source
    var remarks by TransactionTable.txn_source
    var session by TransactionTable.session
    var createdAt by TransactionTable.created_at
}
