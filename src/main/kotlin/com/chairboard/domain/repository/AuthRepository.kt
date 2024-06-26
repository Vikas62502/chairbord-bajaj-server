package com.chairboard.domain.repository

import com.chairboard.database.dbQuery
import com.chairboard.database.entity.User
import com.chairboard.database.tables.UsersTable
import com.chairboard.dto.UserResponse
import com.chairboard.dto.UserRow
import com.chairboard.utils.generateRandomId
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AuthRepository : Repository {

    suspend fun insert(input: UserRow): User {
        return dbQuery {
             transaction {
                return@transaction User.new(generateRandomId(input.mobile)) {
                    username = input.username
                    password = input.password
                    mobile = input.mobile
                    isActive = true
                    role = input.role
                    associatedAgentId = input.associatedAgentId
                }
            }
        }
    }

    fun getByUsername(username: String): User? {
        return transaction {
            val user = User.find { UsersTable.username eq username }.singleOrNull()
            user?.let {
                return@let user
            } ?: return@transaction null
        }
    }

    fun getByMobile(mobile: String): User? {
        return transaction {
            User.find { UsersTable.mobile eq mobile }.singleOrNull()
        }
    }

    suspend fun getById(id: String): User? {
        return dbQuery {
            transaction {
                return@transaction User.findById(id)
            }
        }

    }
}