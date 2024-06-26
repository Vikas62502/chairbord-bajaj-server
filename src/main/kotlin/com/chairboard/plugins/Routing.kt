package com.chairboard.plugins

import com.chairboard.domain.service.*
import com.chairboard.plugins.Modules.fastagRepository
import com.chairboard.plugins.Modules.txnRepository
import com.chairboard.plugins.Modules.userRepository
import com.chairboard.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val userService = UserService(userRepository = userRepository)
    val fastagService = FastagService(userRepository = userRepository, fastagRepository = fastagRepository)
    val txnService = TxnService(userRepository, txnRepository)
    val customerService = CustomerService()
    val documentService = DocumentService()

    routing {
        authenticate("auth-jwt") {
            route("/api/customer/auth") {
                customerAuth()
            }
            route("/api") {
                documentRoute(documentService)
            }
            route("/api/customer") {
                customerRoute(customerService, documentService)
            }

            route("/api/user") {
                userRoute()
            }

            route("/api/customer/vehicle") {
                vehicleRoute(userService)
            }

            route("/api/customer/fastag") {
                fastagRoute(userService, fastagService)
            }

            route("/api/admin") {
                adminRoute(userService, txnService, fastagService)
            }

            route("/api/customer/wallet") {
                walletRoute(txnService)
            }
        }
    }
}

fun Application.configureAuthRouting() {
    val jwtService = JwtService(application = this, userRepository = userRepository)
    val authService = AuthService(
        jwtService = jwtService,
        authRepository = Modules.authRepository,
        refreshTokenRepository = Modules.refreshTokenRepository
    )

    routing {
        get("/health") {
            call.respond("OK")
        }
        route("/api/auth") {
            authRoute(authService)
        }
    }
}
