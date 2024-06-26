package com.chairboard.dto

import com.chairboard.utils.AesEncryption
import com.google.gson.Gson
import io.ktor.http.*
import kotlinx.serialization.Serializable

sealed interface Result {
    data class Success<out R>(
        val status: HttpStatusCode = HttpStatusCode.OK,
        val success: Boolean = true,
        val data: R,
    ) : Result

    data class Failure<out E>(
        val status: HttpStatusCode = HttpStatusCode.InternalServerError,
        val error: E
    ) : Result
}

data class ErrorData(
    val errorCode: String = "",
    val messages: List<String> = listOf(),
)

@Serializable
data class ClientResponse(
    val msg: String,
    val status: String,
    val code: String,
    val errorCode: String,
    val errorDesc: String,
)

fun String.decrypt() = AesEncryption.decrypt(this)

fun String.toClientError() = Gson().fromJson(this, ClientResponse::class.java)