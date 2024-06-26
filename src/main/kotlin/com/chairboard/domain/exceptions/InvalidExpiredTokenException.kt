package com.chairboard.domain.exceptions

import com.chairboard.dto.ErrorData
import com.chairboard.dto.Result
import io.ktor.http.*

class InvalidExpiredTokenException(
    private val httpStatusCode: HttpStatusCode = HttpStatusCode.Unauthorized,
    msg: String = "Unauthorized access") : Exception(msg) {
    private val errorCode = "AUTH003"
    fun toErrorResponse() = Result.Failure(
        status = httpStatusCode,
        error = ErrorData(
            errorCode = errorCode,
            messages = listOf(message ?: errorCode)
        )
    )
}