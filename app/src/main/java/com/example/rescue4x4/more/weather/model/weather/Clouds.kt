package com.example.rescue4x4.more.weather.model.weather

import com.google.gson.annotations.SerializedName

data class Clouds (
    @SerializedName("all") var all: Int? = null
)