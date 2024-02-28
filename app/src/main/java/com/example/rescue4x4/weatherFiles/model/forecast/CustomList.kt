package com.example.rescue4x4.weatherFiles.model.forecast

import com.example.rescue4x4.weatherFiles.model.weather.Clouds
import com.example.rescue4x4.weatherFiles.model.weather.Main
import com.example.rescue4x4.weatherFiles.model.weather.Sys
import com.example.rescue4x4.weatherFiles.model.weather.Weather
import com.example.rescue4x4.weatherFiles.model.weather.Wind
import com.google.gson.annotations.SerializedName

data class CustomList(
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("weather") var weather: ArrayList<Weather>? = arrayListOf(),
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("visibility") var visiblity: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("dt_txt") var dtTxt: String? = null,
)