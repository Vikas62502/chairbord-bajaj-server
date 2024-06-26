package com.chairboard.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*

object HttpClient {
    lateinit var instance: HttpClient
    const val BASE_ENDPOINT = "/ftAggregatorService/v1"
    const val CHANNEL = "CBPL"

    fun init(application: Application) {
        val baseUrl = application.environment.config.tryGetString("client.baseurl") ?: ""
        println(baseUrl)
        val subscriptionKey = application.environment.config.tryGetString("client.subscription") ?: ""
        instance =
                HttpClient(OkHttp) {
                    defaultRequest {
                        url(baseUrl)
                        contentType(ContentType.Text.Plain)
                        headers {
                                    append("Ocp-Apim-Subscription-Key", subscriptionKey)
                                    append("aggr_channel", CHANNEL)
                                }
                                .build()
                    }
                }
    }
}
