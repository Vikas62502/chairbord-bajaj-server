package com.chairboard.utils

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.apache.commons.codec.binary.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object AesEncryption {
    private lateinit var secretKey: String
    private lateinit var salt: String
    private lateinit var iv: IvParameterSpec

    fun init(application: Application) {
        secretKey = application.environment.config.tryGetString("client.secret") ?: ""
        salt = application.environment.config.tryGetString("client.salt") ?: ""
        iv = generateIv(salt = salt)
    }


    fun decrypt(encryptedText: String, secretKey: String = this.secretKey): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val key = SecretKeySpec(secretKey.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val decryptedText = cipher.doFinal(Base64.decodeBase64(encryptedText))
        return String(decryptedText)
    }

    fun encrypt(data: String, secretKey: String = this.secretKey): String {
        val sKeySpec = SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv)
        val encrypted = cipher.doFinal(data.toByteArray())
        return Base64.encodeBase64String(encrypted)
    }

    private fun generateIv(salt: String): IvParameterSpec {
        return IvParameterSpec(salt.toByteArray(charset("UTF-8")))
    }
}