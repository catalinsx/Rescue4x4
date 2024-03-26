package com.example.rescue4x4.more.weather.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescue4x4.more.weather.model.forecast.ForecastResult
import com.example.rescue4x4.more.weather.model.weather.WeatherResult
import com.example.rescue4x4.more.weather.network.RetrofitClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch


enum class STATE{
    LOADING,
    SUCCESS,
    FAILED
}

/*
 ViewModel

 A ViewModel is a part of Android Architecture Components. It is a class that's designed to store
 and manage UI-related data in a lifecycle conscious way. In simpler terms it is like a container
    that holds data needed for the UI. It is a storehouse where you keep data and logic that is needed
    for the UI

    1.Is it used to separate the data from the UI logic. It is used to store and manage UI-related data
    2.ViewModels are lifecycle aware, meaning they survive configuration changes like screen rotations
    3. Code reusability
 */

class MainViewModel : ViewModel() {
    // controlling the state for the view model
    var state by mutableStateOf(STATE.LOADING)
    // hold value from api for weather info
    var weatherResponse : WeatherResult by mutableStateOf(WeatherResult())
    // hold value from api for forecast info
    var forecastResponse : ForecastResult by mutableStateOf(ForecastResult())
    var errorMessage: String by mutableStateOf("")

    fun getWeatherByLocation(latLng : LatLng){
        Log.d("apiweather", "api is called here")
        // initiates a coroutine within the viewModelScope. Coroutines are light-weight threads
        // that can be used to perform tasks asynchronously, can suspend execution without blocking
        // the main thread. Here in this case, we are making a network call to get the weather info
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