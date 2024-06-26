package com.chairboard.domain.repository

import com.chairboard.database.entity.AuthUser
import com.chairboard.database.entity.User
import com.chairboard.database.tables.AuthUsersTable
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class RefreshTokenRepository : Repository {


    fun findMobileByRefreshToken(token: String): String? {
        return transaction {
            return@transaction AuthUser.find { AuthUsersTable.refreshToken eq token }.singleOrNull()?.user?.mobile
        }
    }


    fun save(token: String, inputUser: User) {
        transaction {
            AuthUser.new(UUID.randomUUID().toString()) {
                user = inputUser
                refreshToken = token
                lastLogin = LocalDateTime.now()
            }
        }
    }

}