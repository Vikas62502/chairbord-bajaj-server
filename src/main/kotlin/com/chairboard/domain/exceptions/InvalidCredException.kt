package com.chairboard.domain.exceptions

import com.chairboard.dto.ErrorData
import com.chairboard.dto.Result
import io.ktor.http.*

class InvalidCredException(
    private val httpStatusCode: HttpStatusCode = HttpStatusCode.Unauthorized,
    msg: String = "Entered credentials are not valid") : Exception(msg) {
    private val errorCode = "AUTH002"
    fun toErrorResponse() = Result.Failure(
        status = httpStatusCode,
        error = ErrorData(
            errorCode = errorCode,
            messages = listOf(message ?: errorCode)
        )
    )
}