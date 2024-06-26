package com.chairboard

import com.chairboard.client.HttpClient
import com.chairboard.database.Database
import com.chairboard.plugins.*
import com.chairboard.utils.AesEncryption
import com.chairboard.utils.generateRandomId
import com.chairboard.utils.getRequestTime
import io.ktor.server.application.*
import io.ktor.server.netty.*
import java.time.LocalDateTime

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    Modules.init(this)
    Database.init(this)
    AesEncryption.init(this)
    HttpClient.init(this)
    configureSecurity()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureAuthRouting()
    configureRouting()
}
