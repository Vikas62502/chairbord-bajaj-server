package com.chairboard.database.entity

import com.chairboard.database.tables.DocumentTable
import com.chairboard.dto.customer.DocumentResponse
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Document(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Document>(DocumentTable)

    var session by DocumentTable.session
    var docType by DocumentTable.docType
    var docNo by DocumentTable.docNo
    var expiryDate by DocumentTable.expiryDate
    var customer by Customer referencedOn DocumentTable.customer
    var created_at by DocumentTable.created_at
    fun toResponse(ignoreCustomer: Boolean = true) =
        DocumentResponse(
            session = session,
            docType = docType,
            docNo = docNo,
            expiryDate = expiryDate,
            customer = if (ignoreCustomer) null else customer.toResponse(),
            created_at = created_at.toString()
        )
}
