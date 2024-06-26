package com.chairboard.database.entity

import com.chairboard.database.tables.CustomersTable
import com.chairboard.database.tables.FastagTable
import com.chairboard.database.tables.UsersTable
import com.chairboard.dto.UserResponse
import com.chairboard.dto.customer.CustomerResponse
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Customer(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Customer>(CustomersTable)

    var name by CustomersTable.name
    var lastName by CustomersTable.lastname
    var mobile by CustomersTable.mobile
    var dob by CustomersTable.dob
    var session by CustomersTable.session
    var createdAt by CustomersTable.created_at
    var walletStatus by CustomersTable.walletStatus
    var kycStatus by CustomersTable.kycStatus
    var walletId by CustomersTable.walletId
    fun toResponse(ignoreUser: Boolean = true) =
        CustomerResponse(
            id = id.value,
          name = name,
            lastName = lastName,
            mobile = mobile,
            dob = dob,
            session = session,
            createdAt = createdAt.toString(),
            user = null,
            walletId = walletId,
            kycStatus = kycStatus,
            walletStatus = walletStatus,
        )
}
