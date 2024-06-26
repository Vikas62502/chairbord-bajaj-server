package com.chairboard.utils

import com.chairboard.client.HttpClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun getRequestTime(): String {
    val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:MM:SS.SSS")
    return LocalDateTime.now().format(formatter)
}

fun generateRandomId(mobile: String): String {
    return HttpClient.CHANNEL+(System.currentTimeMillis()/1000).toString() + mobile.substring(0, 4)
}