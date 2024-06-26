package com.chairboard.dto.customer.fastag

import kotlinx.serialization.Serializable

@Serializable
data class UpdateFastagStatus(
    val id: String,
    val status: String,
)
