package com.chairboard.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object CustomersTable : IdTable<String>(name = "customers") {
    override val id: Column<EntityID<String>> = varchar("id", 128).uniqueIndex().entityId()
    val session = varchar(name = "session", length = 128)
    val name = varchar(name = "name", length = 56)
    val lastname = varchar(name = "last_name", length = 56)
    val mobile = varchar(name = "mobile", length = 12)
    val dob = varchar(name = "dob", length = 12)
    val walletStatus = varchar(name = "wallet_status", length = 56).nullable()
    val kycStatus = varchar(name = "kyc_status", length = 56).nullable()
    val walletId = varchar(name = "wallet_id", length = 128).nullable()
    val created_at = datetime(name = "created_at").defaultExpression(CurrentDateTime)
}