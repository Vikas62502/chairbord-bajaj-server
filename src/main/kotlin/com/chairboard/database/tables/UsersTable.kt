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

object UsersTable : IdTable<String>(name = "users") {
    override val id: Column<EntityID<String>> = varchar("id", 128).uniqueIndex().entityId()
    val username = varchar(name = "username", length = 128)
    val password = varchar(name = "password", length = 128)
    val mobile = varchar(name = "mobile", length = 12)
    val active = bool(name = "active").default(defaultValue = true)
    val balance = integer(name = "wallet_balance").default(0)
    val role = varchar(name = "role", length = 10).default(defaultValue = ROLE_AGENT)
    val associated_agent_id = varchar(name = "associated_agent_id", length = 56).nullable()
    val created_at = datetime(name = "created_at").defaultExpression(CurrentDateTime)
}