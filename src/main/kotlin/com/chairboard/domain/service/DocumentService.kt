package com.chairboard.domain.service

import com.chairboard.dto.customer.CustomerResponse
import com.chairboard.dto.customer.DocumentResponse
import com.chairboard.dto.customer.DocumentRow
import com.chairboard.dto.customer.wallet.CreateWalletClientRequest
import com.chairboard.plugins.Modules.customerRepository
import com.chairboard.plugins.Modules.documentRepository

class DocumentService : Service {

    fun insertRow(customerID: String, input: CreateWalletClientRequest) {
        input.doc.forEach { docItem ->
            val customer = customerRepository.getById(customerID)
            val row = DocumentRow(
                session = input.sessionId,
                docNo = docItem.docNo,
                docType = docItem.docType,
                expiryDate = docItem.expiryDate,
            )
             documentRepository.insert(input = row, customer!!)
        }
    }


    fun getDocumentsById(id: String): DocumentResponse? {
        return documentRepository.getById(id)?.toResponse()
    }

    fun getDocumentsForCustomer(customerId: String): List<DocumentResponse> {
            return documentRepository.getAllDocumentsForCustomer(customerId).map {
                it.toResponse()
            }
    }

    fun getAllDocumentsForSession(sessionId: String): List<DocumentResponse> {
        return documentRepository.getAllBySessionId(sessionId).map {
            it.toResponse()
        }
    }

}