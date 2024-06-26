package com.chairboard.database.tables

import com.chairboard.database.tables.AuthUsersTable.defaultExpression
import com.chairboard.utils.ROLE_AGENT
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object DocumentTable : IdTable<String>(name = "documents") {
    override val id: Column<EntityID<String>> = varchar("id", 128).entityId()
    val session = varchar(name = "session", length = 128)
    val docType = integer(name = "doc_type")
    val docNo = varchar(name = "doc_no", length = 56)
    val expiryDate = varchar(name = "expiry_date", length = 16)
    val customer = reference("customer", CustomersTable)
    val created_at = datetime(name = "created_at").defaultExpression(CurrentDateTime)
}