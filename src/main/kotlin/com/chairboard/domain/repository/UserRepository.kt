package com.chairboard.domain.repository

import com.chairboard.database.entity.User
import com.chairboard.database.tables.UsersTable
import com.chairboard.dto.UserResponse
import com.chairboard.dto.UserRow
import com.chairboard.utils.ROLE_AGENT
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepository : Repository {

    fun getAll(): List<UserResponse> {
        return transaction {
            return@transaction User.all().orderBy(UsersTable.created_at to SortOrder.DESC).filter { it.role == ROLE_AGENT }.map {
                it.toResponse()
            }
        }
    }

    fun updateWalletBalance(id: String, balance:Int): User? {
        return transaction {
            val user = User.findById(id)
            user?.walletBalance = balance
            return@transaction user
        }
    }

    fun getById(id: String): User? {
        return transaction {
            return@transaction User.findById(id)
        }
    }

    fun changePass(userId: String, password: String): Boolean {
        return transaction {
            User.findById(userId)?.let {
                it.password = password
            }
            return@transaction true
        }
    }
    fun toggleActive(userId: String, activate: Boolean): Boolean {
        return transaction {
            var success = true
            User.findById(userId)?.let {
                it.isActive = activate
            } ?: run { success = false }
            return@transaction success
        }
    }
}