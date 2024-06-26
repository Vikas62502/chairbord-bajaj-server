package com.chairboard.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object TransactionTable : IdTable<String>(name = "user_wallet") {
    override val id: Column<EntityID<String>> = varchar("id", 128).entityId()
    val user = reference("user", UsersTable)
    val balance = integer(name = "wallet_balance").default(0)
    val txn_type = varchar(name = "txn_type", length = 10)
    val txn_amount = integer(name = "txn_amount")
    val mobile = varchar(name = "mobile", length = 12)
    val txn_source = varchar(name = "txn_source", length = 128)
    val remarks = varchar(name = "remarks", length = 128).default("")
    val session = varchar(name = "session", length = 128).nullable().default("")
    val reference = varchar(name = "reference", length = 56).default("")
    val created_at = datetime(name = "created_at").defaultExpression(CurrentDateTime)
}