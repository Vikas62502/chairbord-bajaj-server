package com.chairboard.domain.service

import com.auth0.jwt.interfaces.DecodedJWT
import com.chairboard.database.entity.User
import com.chairboard.domain.repository.AuthRepository
import com.chairboard.domain.repository.RefreshTokenRepository
import com.chairboard.dto.*
import com.chairboard.utils.Cipher
import com.chairboard.utils.JwtProvider
import java.util.*

class AuthService(
    private val jwtService: JwtService,
    private val authRepository: AuthRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
) : Service {
    private val base64Encoder = Base64.getEncoder()
    suspend fun insertUser(input: UserRow): String {
        return authRepository.insert(input).id.value
    }

    fun getUser(mobile: String): User? {
        return authRepository.getByMobile(mobile)
    }
    suspend fun getById(id: String): User? {
        return authRepository.getById(id)
    }

    suspend fun authenticate(loginRequest: LoginRequest): LoginRegisterResponse? {
        val userId = loginRequest.userId
        val foundUser = authRepository.getById(userId)

        return if (foundUser != null && String(base64Encoder.encode(Cipher.encrypt(loginRequest.password))) == foundUser.password) {
            val accessToken = jwtService.createAccessToken(foundUser.toResponse())
            val refreshToken = jwtService.createRefreshToken(foundUser.toResponse())

            refreshTokenRepository.save(refreshToken, foundUser)

            LoginRegisterResponse(
                username = foundUser.username,
                token = accessToken,
                refreshToken = refreshToken,
                id = foundUser.id.value,
                associatedAgentId = foundUser.associatedAgentId ?: "",
                role = foundUser.role,
                isActive = foundUser.isActive,
            )
        } else
            null
    }

    fun refreshToken(token: String): RefreshTokenResponse? {
        val decodedRefreshToken = verifyRefreshToken(token)
        val mobile = refreshTokenRepository.findMobileByRefreshToken(token)

        return if (decodedRefreshToken != null && mobile != null) {
            val foundUser: User? = authRepository.getByMobile(mobile)
            val id: String? = decodedRefreshToken.getClaim("id").asString()

            if (foundUser != null && id == foundUser.id.value) {
                val user = foundUser.toResponse()
                RefreshTokenResponse(jwtService.createAccessToken(user), jwtService.createRefreshToken(user))
            } else
                null
        } else
            null
    }

    private fun verifyRefreshToken(token: String): DecodedJWT? {
        val decodedJwt: DecodedJWT? = getDecodedJwt(token)

        return decodedJwt?.let {
            val audienceMatches = jwtService.audienceMatches(it.audience.first())

            if (audienceMatches)
                decodedJwt
            else
                null
        }
    }

    private fun getDecodedJwt(token: String): DecodedJWT? =
        try {
            JwtProvider.varifier.verify(token)
        } catch (ex: Exception) {
            null
        }
}