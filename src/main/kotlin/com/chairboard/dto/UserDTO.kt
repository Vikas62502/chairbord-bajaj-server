package com.chairboard.dto

import com.chairboard.utils.ROLE_AGENT
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: String,
    val username: String,
    val mobile: String,
    val associatedAgentId: String,
    val isActive: Boolean = true,
    val role: String = ROLE_AGENT,
    val walletBalance: Int,
)

@Serializable
data class UserRow(
    val username: String,
    val password: String,
    val mobile: String,
    val associatedAgentId: String,
    val isActive: Boolean,
    val role: String = ROLE_AGENT,
)