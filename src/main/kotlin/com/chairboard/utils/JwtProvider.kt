package com.chairboard.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*

object JwtProvider {
    lateinit var varifier: JWTVerifier

    fun init(app: Application) {
        val secret = getConfigProperty(app, "jwt.secret")
        val issuer = getConfigProperty(app, "jwt.issuer")
        val audience = getConfigProperty(app, "jwt.audience")
        varifier = JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }

    private fun getConfigProperty(application: Application, path: String) =
        application.environment.config.property(path).getString()
}
