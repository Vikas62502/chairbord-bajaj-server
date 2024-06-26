package com.chairboard.dto.customer.vehicle

import com.chairboard.dto.ClientResponse
import kotlinx.serialization.Serializable

@Serializable
data class VehicleMakerResponse(
    val response: ClientResponse,
    val vehicleMakerList: List<String>,
)
