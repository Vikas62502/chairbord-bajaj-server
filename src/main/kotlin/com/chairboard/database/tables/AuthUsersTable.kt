package com.chairboard.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object AuthUsersTable : IdTable<String>(name = "auth_users") {
    override val id: Column<EntityID<String>> = varchar("id", 128).uniqueIndex().entityId()
    val user = reference("user", UsersTable)
    val lastLogin = datetime("last_login").defaultExpression(CurrentDateTime)
    val refreshToken = varchar(name = "refreshToken", length = 512)
}