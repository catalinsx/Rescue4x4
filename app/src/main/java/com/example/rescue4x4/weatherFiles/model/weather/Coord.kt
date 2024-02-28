package com.example.rescue4x4.weatherFiles.model.weather

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("lan") var lan: Double? = null
)