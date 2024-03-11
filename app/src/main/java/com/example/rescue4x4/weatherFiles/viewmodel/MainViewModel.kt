package com.example.rescue4x4.weatherFiles.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescue4x4.weatherFiles.model.forecast.ForecastResult
import com.example.rescue4x4.weatherFiles.model.weather.WeatherResult
import com.example.rescue4x4.weatherFiles.network.RetrofitClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch


enum class STATE{
    LOADING,
    SUCCESS,
    FAILED
}

class MainViewModel : ViewModel() {
    // controlling the state for the view model
    var state by mutableStateOf(STATE.LOADING)
    // hold value from api for weather info
    var weatherResponse : WeatherResult by mutableStateOf(WeatherResult())
    // hold value from api for forecast info
    var forecastResponse : ForecastResult by mutableStateOf(ForecastResult())
    var errorMessage: String by mutableStateOf("")

    fun getWeatherByLocation(latLng : LatLng){
        Log.d("catalin", "api is called here")
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try{
                val apiResponse = apiService.getWeather(latLng.latitude, latLng.longitude)
                weatherResponse = apiResponse
                state = STATE.SUCCESS
            } catch (e: Exception){
                errorMessage = e.message!!.toString()
            }
        }
    }

    fun getForecastByLocation(latLng : LatLng){
        viewModelScope.launch {
            state = STATE.LOADING
            val apiService = RetrofitClient.getInstance()
            try{
                val apiResponse = apiService.getForecast(latLng.latitude, latLng.longitude)
                forecastResponse = apiResponse
                state = STATE.SUCCESS
            } catch (e: Exception){
                errorMessage = e.message!!.toString()
            }
        }
    }
}