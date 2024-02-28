package com.example.rescue4x4.weatherFiles.network

import com.example.rescue4x4.weatherFiles.Const.Companion.openWeatherApiKey
import com.example.rescue4x4.weatherFiles.model.forecast.ForecastResult
import com.example.rescue4x4.weatherFiles.model.weather.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherApiKey

    ) : WeatherResult

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherApiKey

    ) : ForecastResult

}