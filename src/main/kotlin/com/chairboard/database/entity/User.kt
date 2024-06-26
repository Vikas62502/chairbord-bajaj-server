package com.chairboard.database.entity

import com.chairboard.database.tables.UsersTable
import com.chairboard.dto.UserResponse
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, User>(UsersTable)

    var username by UsersTable.username
    var password by UsersTable.password
    var associatedAgentId by UsersTable.associated_agent_id
    var mobile by UsersTable.mobile
    var isActive by UsersTable.active
    var role by UsersTable.role
    var walletBalance by UsersTable.balance
    fun toResponse() =
        UserResponse(
            userId = id.value,
            username = username,
            mobile = mobile,
            associatedAgentId = associatedAgentId ?: "",
            role = role,
            isActive = isActive,
            walletBalance = walletBalance,
        )
}
