package com.chairboard.plugins

import com.chairboard.domain.exceptions.InvalidExpiredTokenException
import com.chairboard.domain.service.JwtService
import com.chairboard.dto.UserResponse
import com.chairboard.utils.JwtProvider
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

fun Application.configureSecurity() {
    JwtProvider.init(this)
    val jwtService = JwtService(this, Modules.userRepository)

    authentication {
        jwt(name = "auth-jwt") {
            verifier(JwtProvider.varifier)
            validate { credential ->
                jwtService.customValidator(credential)
            }
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = InvalidExpiredTokenException().toErrorResponse()
                )
            }
        }
    }
}

@Serializable
data class AuthUser(
    val username: String,
    val mobile: String,
    val id: String,
    val associatedAgentId: String,
    val role: String,
    val active: Boolean,
    val walletBalance: Int,
) : Principal {
    fun toResponse() = UserResponse(id, username, mobile, associatedAgentId, active, role, walletBalance)
}
