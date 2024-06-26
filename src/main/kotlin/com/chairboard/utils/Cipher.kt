package com.chairboard.utils

import com.auth0.jwt.algorithms.Algorithm

object Cipher {
    val algorithm: Algorithm = Algorithm.HMAC256("chairboard-password")

    fun encrypt(data: String?): ByteArray =
        algorithm.sign(data?.toByteArray())

    fun decrypt(data: String) = algorithm.verify(JwtProvider.varifier.verify(""))
}
