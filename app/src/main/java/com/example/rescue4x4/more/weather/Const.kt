package com.example.rescue4x4.more.weather

class Const {
    companion object{
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val openWeatherApiKey = "api key here"

        const val colorBg1 = 0xFF08203E
        const val colorBg2 = 0xFF557C93
        const val cardColor = 0xFF333639

        const val LOADING = "Loading..."
        const val NA = "N/A"



    }
}