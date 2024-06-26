package com.chairboard.dto.customer

import com.chairboard.dto.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse(
    val id: String,
    val session: String,
    val name: String,
    val lastName: String,
    val mobile: String,
    val dob: String,
    val createdAt: String,
    val walletStatus: String?,
    val kycStatus: String?,
    val walletId: String?,
    val user: UserResponse?,
)

@Serializable
data class CustomerRow(
    val session: String,
    val name: String,
    val lastName: String,
    val mobile: String,
    val dob: String,
    val walletStatus: String?,
    val kycStatus: String?,
    val walletId: String?,

)
