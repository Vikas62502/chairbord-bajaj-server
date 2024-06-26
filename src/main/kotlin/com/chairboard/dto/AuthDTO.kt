package com.chairboard.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val userId: String,
    val password: String,
)

@Serializable
data class ChangePassRequest(
    val userId: String,
    val password: String,
)

@Serializable
data class ToggleActiveStatus(
    val userId: String,
    val active: Int,
)

@Serializable
data class LoginRegisterResponse(
    val username: String,
    val id: String,
    val associatedAgentId: String,
    val token: String,
    val refreshToken: String,
    val role: String,
    val isActive: Boolean
)

@Serializable
data class RegisterRequest(
    val username: String,
    val mobile: String,
    val password: String,
    val associatedAgentId: String,
    val role: String,
) {
    fun toUserRow() = UserRow(
        username = username,
        password = password,
        mobile = mobile,
        associatedAgentId = associatedAgentId,
        isActive = true,
        role = role,
    )

    fun toLoginRequest() = LoginRequest(mobile, password)
}

@Serializable
data class RefreshTokenRequest(
    val token: String,
)

@Serializable
data class RefreshTokenResponse(
    val token: String,
    val refreshToken: String,
)