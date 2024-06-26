package com.chairboard.routes

import com.chairboard.domain.exceptions.*
import com.chairboard.domain.service.AuthService
import com.chairboard.dto.*
import com.chairboard.plugins.AuthUser
import com.chairboard.utils.Cipher
import com.chairboard.utils.ROLE_ADMIN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.authRoute(authService: AuthService) {
    val base64Encoder = Base64.getEncoder()

    post("/register") {
        val registerRequest = call.receive<RegisterRequest>()
        val foundUser = null/*authService.getUser(registerRequest.mobile)*/
        foundUser?.let {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = UserAlreadyExistException().toErrorResponse()
            )
        } ?: run {
            val userId = authService.insertUser(
                input = registerRequest.toUserRow().copy(
                    password = String(
                        base64Encoder.encode(
                            Cipher.encrypt(registerRequest.password)
                        )
                    )
                )
            )
            val user = authService.getById(userId)?.toResponse()
//            val authResponse: LoginRegisterResponse? = authService.authenticate(registerRequest.toLoginRequest())
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = user)
            )
        }
    }

    post("/login") {
        val loginRequest = call.receive<LoginRequest>()
        val authResponse: LoginRegisterResponse? = authService.authenticate(loginRequest)

        authResponse?.let {
            if (it.isActive) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = Result.Success(data = authResponse)
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = UserInactiveException().toErrorResponse(),
                )
            }
        } ?: call.respond(
            status = HttpStatusCode.Unauthorized,
            message = InvalidCredException().toErrorResponse()
        )
    }

    post("/token/refresh") {
        val request = call.receive<RefreshTokenRequest>()
        val refreshTokenResponse = authService.refreshToken(token = request.token)

        refreshTokenResponse?.let {
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = it)
            )
        } ?: call.respond(
            status = HttpStatusCode.Unauthorized,
            message = InvalidExpiredTokenException().toErrorResponse()
        )
    }
}
