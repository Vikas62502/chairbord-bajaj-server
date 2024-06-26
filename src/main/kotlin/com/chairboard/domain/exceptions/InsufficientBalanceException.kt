package com.chairboard.domain.exceptions

import com.chairboard.dto.ErrorData
import com.chairboard.dto.Result
import io.ktor.http.*

class InsufficientBalanceException(
    private val httpStatusCode: HttpStatusCode = HttpStatusCode.BadRequest,
    msg: String = "You do not have enough wallet balance") : Exception(msg) {
    private val errorCode = "AUTH006"
    fun toErrorResponse() = Result.Failure(
        status = httpStatusCode,
        error = ErrorData(
            errorCode = errorCode,
            messages = listOf(message ?: errorCode)
        )
    )
}