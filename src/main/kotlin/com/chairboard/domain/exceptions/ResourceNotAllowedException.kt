package com.chairboard.domain.exceptions

import com.chairboard.dto.ErrorData
import com.chairboard.dto.Result
import io.ktor.http.*

class ResourceNotAllowedException(
    private val httpStatusCode: HttpStatusCode = HttpStatusCode.Unauthorized,
    msg: String = "You are not allowed to access the resource"
) : Exception(msg) {
    private val errorCode = "AUTH005"
    fun toErrorResponse() = Result.Failure(
        status = httpStatusCode,
        error = ErrorData(
            errorCode = errorCode,
            messages = listOf(message ?: errorCode)
        )
    )
}