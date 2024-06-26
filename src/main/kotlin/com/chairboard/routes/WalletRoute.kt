package com.chairboard.routes

import com.chairboard.domain.exceptions.ResourceNotAllowedException
import com.chairboard.domain.service.TxnService
import com.chairboard.dto.Result
import com.chairboard.dto.customer.txn.AddTransactionResponse
import com.chairboard.dto.customer.txn.AllTransactions
import com.chairboard.dto.customer.txn.TxnRequest
import com.chairboard.plugins.AuthUser
import com.chairboard.utils.ROLE_ADMIN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.walletRoute(txnService: TxnService) {

    post("/transaction") {
        val agent = call.authentication.principal<AuthUser>()
        if (agent?.role == ROLE_ADMIN) {
            val request = call.receive<TxnRequest>()
            val newBalance = txnService.insertRow(agent.id, request)
            call.respond(
                status = HttpStatusCode.Created,
                message = Result.Success(data = AddTransactionResponse(balance = newBalance)))
        } else {
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ResourceNotAllowedException().toErrorResponse())
        }

    }

    get("/transactions") {
        val transactions = txnService.getAll()
        call.respond(
            status = HttpStatusCode.OK,
            message = Result.Success(data = AllTransactions(transactions = transactions)))
    }

    get("/transactions") {
        val agent = call.authentication.principal<AuthUser>()
        val txnType = call.request.queryParameters["txnType"]
        val sortBy = call.request.queryParameters["order"] ?: "latest"
        val transactions = txnService.getAll()
        call.respond(
            status = HttpStatusCode.OK,
            message = Result.Success(data = AllTransactions(transactions = transactions)))
    }
}
