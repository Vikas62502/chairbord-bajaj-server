package com.chairboard.database.entity

import com.chairboard.database.tables.AuthUsersTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AuthUser(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, AuthUser>(AuthUsersTable)

    var user by User referencedOn AuthUsersTable.user
    var lastLogin by AuthUsersTable.lastLogin
    var refreshToken by AuthUsersTable.refreshToken
}
