package com.example.rescue4x4.more.weather.model.forecast

import com.google.gson.annotations.SerializedName

data class ForecastResult(
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var list: ArrayList<CustomList>? = arrayListOf(),
    @SerializedName("city") var city: City? = City(),

    )