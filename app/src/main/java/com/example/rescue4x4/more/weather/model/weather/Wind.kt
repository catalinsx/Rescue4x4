package com.example.rescue4x4.more.weather.model.weather

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null,

    )