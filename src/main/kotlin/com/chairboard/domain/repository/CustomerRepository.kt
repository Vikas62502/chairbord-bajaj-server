package com.chairboard.domain.repository

import com.chairboard.database.entity.Customer
import com.chairboard.database.entity.User
import com.chairboard.database.tables.CustomersTable
import com.chairboard.dto.customer.CustomerRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CustomerRepository : Repository {

    fun insert(input: CustomerRow, agent: User): String {
        return transaction {
            return@transaction Customer.new(UUID.randomUUID().toString()) {
                name = input.name
               mobile = input.mobile
                dob = input.dob
                lastName = input.lastName
                session = input.session
                walletId = input.walletId
                kycStatus = input.kycStatus
                walletStatus = input.walletStatus
            }
        }.id.value
    }

    fun getById(id:String): Customer? {
        return transaction {
            return@transaction Customer.findById(id)
        }
    }


    fun getCustomerBySession(session: String) : Customer? {
        return transaction {
            return@transaction Customer.find { CustomersTable.session eq session }.orderBy(CustomersTable.created_at to SortOrder.DESC).firstOrNull()
        }
    }
}