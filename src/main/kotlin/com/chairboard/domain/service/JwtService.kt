package com.chairboard.domain.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.chairboard.database.entity.User
import com.chairboard.domain.repository.UserRepository
import com.chairboard.dto.UserResponse
import com.chairboard.plugins.AuthUser
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

class JwtService(
    private val application: Application,
    private val userRepository: UserRepository,
) {
    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")
    private val tokenValidity = 60000 * 60 * 10 // 10 hours
    private val refreshTokenValidity = 60000 * 60 * 15 // 15 hours

    fun createAccessToken(user: UserResponse): String =
        createJwtToken(user, tokenValidity)

    fun createRefreshToken(user: UserResponse): String =
        createJwtToken(user, refreshTokenValidity)

    private fun createJwtToken(user: UserResponse, expireIn: Int): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", user.userId)
//            .withExpiresAt(Date(System.currentTimeMillis() + expireIn))
            .sign(Algorithm.HMAC256(secret))


    fun customValidator(
        credential: JWTCredential,
    ): Principal? {
        val userId: String? = extractUserId(credential)
        val foundUser: User? = userId?.let(userRepository::getById)
        println(foundUser)
        return foundUser?.let {
            if (audienceMatches(credential))
                AuthUser(
                    username = foundUser.username,
                    mobile = foundUser.mobile,
                    id = userId,
                    associatedAgentId = foundUser.associatedAgentId ?: "",
                    role = foundUser.role,
                    walletBalance = foundUser.walletBalance,
                    active = foundUser.isActive,
                )
            else
                null
        }
    }

    private fun audienceMatches(
        credential: JWTCredential,
    ): Boolean =
        credential.payload.audience.contains(audience)

    fun audienceMatches(
        audience: String
    ): Boolean =
        this.audience == audience

    private fun getConfigProperty(path: String) =
        application.environment.config.property(path).getString()

    private fun extractUserId(credential: JWTCredential): String? =
        credential.payload.getClaim("userId").asString()
}


