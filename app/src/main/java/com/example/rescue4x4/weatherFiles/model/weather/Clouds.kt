package com.example.rescue4x4.weatherFiles.model.weather

import com.google.gson.annotations.SerializedName

data class Clouds (
    @SerializedName("all") var all: Int? = null
)