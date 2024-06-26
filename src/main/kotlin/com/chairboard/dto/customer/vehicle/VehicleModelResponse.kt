package com.chairboard.dto.customer.vehicle

import com.chairboard.dto.ClientResponse
import kotlinx.serialization.Serializable

@Serializable
data class VehicleModelResponse(
    val response: ClientResponse,
    val vehicleModelList: List<String>,
)