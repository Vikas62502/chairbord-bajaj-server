package com.chairboard.dto.customer

import kotlinx.serialization.Serializable

@Serializable
data class DocumentResponse(
    val session: String,
    val docType: Int,
    val docNo: String,
    val expiryDate: String,
    val customer: CustomerResponse?,
    val created_at: String,
)

@Serializable
data class DocumentRow(
    val session: String,
    val docType: Int,
    val docNo: String,
    val expiryDate: String,
)

@Serializable
data class DocumentsResponse(
    val documents: List<DocumentResponse>
)