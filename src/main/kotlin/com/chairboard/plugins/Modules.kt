package com.chairboard.plugins

import com.chairboard.domain.repository.*
import com.chairboard.domain.service.AuthService
import com.chairboard.domain.service.JwtService
import com.chairboard.domain.service.TxnService
import io.ktor.server.application.*

object Modules {
    lateinit var userRepository: UserRepository
    lateinit var fastagRepository: FastagRepository
    lateinit var authRepository: AuthRepository
    lateinit var txnRepository: TxnRepository
    lateinit var customerRepository: CustomerRepository
    lateinit var documentRepository: DocumentRepository
    lateinit var refreshTokenRepository: RefreshTokenRepository
    private lateinit var jwtService: JwtService
    private lateinit var authService: AuthService

    fun init(application: Application) {
        userRepository = UserRepository()
        authRepository = AuthRepository()
        txnRepository = TxnRepository()
        fastagRepository = FastagRepository()
        documentRepository = DocumentRepository()
        customerRepository = CustomerRepository()
        refreshTokenRepository = RefreshTokenRepository()
        jwtService = JwtService(application = application, userRepository = userRepository)
        authService = AuthService(
            jwtService = jwtService,
            authRepository = authRepository,
            refreshTokenRepository = refreshTokenRepository
        )
    }
}