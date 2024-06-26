package com.chairboard.routes

import com.chairboard.domain.exceptions.ResourceNotAllowedException
import com.chairboard.domain.service.DocumentService
import com.chairboard.dto.Result
import com.chairboard.dto.customer.DocumentsResponse
import com.chairboard.plugins.AuthUser
import com.chairboard.utils.ROLE_ADMIN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.documentRoute(documentService: DocumentService) {
    get("/documents") {
        val agent = call.authentication.principal<AuthUser>()

        if (agent?.role == ROLE_ADMIN) {
            val sessionId = call.request.queryParameters["sessionId"] ?: ""
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = DocumentsResponse(documents = documentService.getAllDocumentsForSession(sessionId)), success = true)
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }

    get("/customer/documents") {
        val agent = call.authentication.principal<AuthUser>()

        if (agent?.role == ROLE_ADMIN) {
            val customerId = call.request.queryParameters["customerId"] ?: ""
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = DocumentsResponse(documents = documentService.getDocumentsForCustomer(customerId)), success = true)
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }
}

