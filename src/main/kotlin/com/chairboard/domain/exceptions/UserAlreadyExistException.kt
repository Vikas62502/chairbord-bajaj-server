package com.chairboard.domain.exceptions

import com.chairboard.dto.ErrorData
import com.chairboard.dto.Result
import io.ktor.http.*

class UserAlreadyExistException(
    private val httpStatusCode: HttpStatusCode = HttpStatusCode.BadRequest,
    msg: String = "User with given mobile already registered, try a different one") :
    Exception(msg) {
    private val errorCode = "AUTH001"
    fun toErrorResponse() = Result.Failure(
        status = httpStatusCode,
        error = ErrorData(
            errorCode = errorCode,
            messages = listOf(message ?: errorCode)
        )
    )
}