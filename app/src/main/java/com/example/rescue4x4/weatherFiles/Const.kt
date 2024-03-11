package com.example.rescue4x4.weatherFiles

import com.example.rescue4x4.R
import com.example.rescue4x4.diagnosis.FAQItem

class Const {
    companion object{
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val openWeatherApiKey = "764d230584a4f1553b662480c30f7d58"

        const val colorBg1 = 0xFF08203E
        const val colorBg2 = 0xFF557C93
        const val cardColor = 0xFF333639

        const val LOADING = "Loading..."
        const val NA = "N/A"



    }
}