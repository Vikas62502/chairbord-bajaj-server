package com.chairboard.dto.customer

import com.chairboard.utils.AesEncryption
import com.google.gson.Gson


interface BaseDTO {
    fun encrypt() = AesEncryption.encrypt(Gson().toJson(this))
}