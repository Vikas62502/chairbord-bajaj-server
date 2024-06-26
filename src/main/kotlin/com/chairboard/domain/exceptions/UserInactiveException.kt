package com.chairboard.domain.exceptions

import com.chairboard.dto.ErrorData
import com.chairboard.dto.Result
import io.ktor.http.*

class UserInactiveException(
    private val httpStatusCode: HttpStatusCode = HttpStatusCode.BadRequest,
    msg: String = "Your account has been deactivated by Admin") :
    Exception(msg) {
    private val errorCode = "AUTH004"
    fun toErrorResponse() = Result.Failure(
        status = httpStatusCode,
        error = ErrorData(
            errorCode = errorCode,
            messages = listOf(message ?: errorCode)
        )
    )
}