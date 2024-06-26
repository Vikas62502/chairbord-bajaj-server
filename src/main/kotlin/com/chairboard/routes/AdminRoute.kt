package com.chairboard.routes

import com.chairboard.domain.exceptions.ResourceNotAllowedException
import com.chairboard.domain.service.FastagService
import com.chairboard.domain.service.TxnService
import com.chairboard.domain.service.UserService
import com.chairboard.dto.ChangePassRequest
import com.chairboard.dto.Result
import com.chairboard.dto.ToggleActiveStatus
import com.chairboard.dto.customer.fastag.UpdateFastagStatus
import com.chairboard.plugins.AuthUser
import com.chairboard.utils.ROLE_ADMIN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.adminRoute(userService: UserService, txnService: TxnService, fastagService: FastagService) {
    getAgents(userService)
    getAgentTxn(txnService)
    changePassword(userService)
    toggleActiveStatus(userService)
    listAllTags(fastagService)
    getFastagHistory(txnService)
    getFastagDetail(fastagService)
}
fun Route.listAllTags(fastagService: FastagService) {
    get("/fastags") {
        val user = call.authentication.principal<AuthUser>()
        if (user?.role == ROLE_ADMIN) {
            val status = call.request.queryParameters["status"] ?: "ALL"
            val agentId = call.request.queryParameters["agentId"] ?: ""
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = fastagService.getAllFastags(status, agentId))
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }
}

    fun Route.getFastagDetail(fastagService: FastagService) {
        get("/fastag") {
            val user = call.authentication.principal<AuthUser>()
            if (user?.role == ROLE_ADMIN) {
                val id = call.request.queryParameters["id"] ?: "NA"
                call.respond(
                    status = HttpStatusCode.OK,
                    message = Result.Success(data = fastagService.getFastagDetail(id))
                )
            } else {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = ResourceNotAllowedException().toErrorResponse()
                )
            }
        }


        post("/fastag/change-status") {
            val user = call.authentication.principal<AuthUser>()
            if (user?.role == ROLE_ADMIN) {
                val request = call.receive<UpdateFastagStatus>()
                fastagService.updateStatus(request.id, request.status)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = Result.Success(data = "Status has been updated")
                )
            } else {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = ResourceNotAllowedException().toErrorResponse()
                )
            }
        }
    }

fun Route.changePassword(userService: UserService) {
    post("/change-password") {
        val user = call.authentication.principal<AuthUser>()
        if (user?.role == ROLE_ADMIN) {
            val changePassRequest = call.receive<ChangePassRequest>()
            userService.changePass(changePassRequest)
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = "Password changed")
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }
}

fun Route.toggleActiveStatus(userService: UserService) {
    post("/toggle-active") {
        val user = call.authentication.principal<AuthUser>()
        if (user?.role == ROLE_ADMIN) {
            val toggleActiveStatus = call.receive<ToggleActiveStatus>()
            val isSuccess = userService.toggleActiveStatus(toggleActiveStatus)
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = if (isSuccess) "Status changed" else "Could not find a user with userId: ${toggleActiveStatus.userId}")
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }
}

fun Route.getAgents(userService: UserService) {
    get("/agents") {
        val user = call.authentication.principal<AuthUser>()
        if (user?.role == ROLE_ADMIN) {
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = userService.getAllAgents())
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }
}

fun Route.getAgentTxn(txnService: TxnService) {
    get("/agents-txn-history") {
        val user = call.authentication.principal<AuthUser>()
        val forAgentId = call.request.queryParameters["agentId"] ?: ""
        val txnType = call.request.queryParameters["txnType"] ?: "ALL"
        if (user?.role == ROLE_ADMIN) {
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = txnService.getAllForAgentByType(forAgentId, txnType))
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }
}

fun Route.getFastagHistory(txnService: TxnService) {
    get("/fastags") {
        val user = call.authentication.principal<AuthUser>()
        val forAgentId = call.request.queryParameters["agentId"] ?: ""
        val txnType = call.request.queryParameters["txnType"] ?: "ALL"
        if (user?.role == ROLE_ADMIN) {
            call.respond(
                status = HttpStatusCode.OK,
                message = Result.Success(data = txnService.getAllForAgentByType(forAgentId, txnType))
            )
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse()
            )
        }
    }
}
