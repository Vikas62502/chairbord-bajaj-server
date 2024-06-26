package com.chairboard.domain.repository

import com.chairboard.database.entity.Customer
import com.chairboard.database.entity.Document
import com.chairboard.database.entity.User
import com.chairboard.database.tables.CustomersTable
import com.chairboard.database.tables.DocumentTable
import com.chairboard.dto.customer.CustomerRow
import com.chairboard.dto.customer.DocumentRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DocumentRepository : Repository {

    fun insert(input: DocumentRow, customerInput: Customer): Document {
        return transaction {
            return@transaction Document.new(UUID.randomUUID().toString()) {
                docType = input.docType
                docNo = input.docNo
                expiryDate = input.expiryDate
                customer = customerInput
                session = input.session
            }
        }
    }

    fun getById(id:String): Document? {
        return transaction {
            return@transaction Document.findById(id)
        }
    }

    fun getAllDocumentsForCustomer(customerId: String): List<Document> {
            return transaction {
                return@transaction Document.find { DocumentTable.customer eq customerId}.orderBy(DocumentTable.created_at to SortOrder.DESC).toList()
            }

    }


    fun getAllBySessionId(session: String) : List<Document> {
        return transaction {
            return@transaction Document.find { DocumentTable.session eq session}.orderBy(DocumentTable.created_at to SortOrder.DESC).toList()
        }
    }
}