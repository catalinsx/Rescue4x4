package com.example.rescue4x4

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

data class GeoCodingResponse(
    val result: List<GeoCodingResult>
)

data class GeoCodingResult(
    val formattedAddress: String
)