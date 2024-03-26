package com.example.rescue4x4.more.weather.network

import com.example.rescue4x4.more.weather.Const.Companion.openWeatherApiKey
import com.example.rescue4x4.more.weather.model.forecast.ForecastResult
import com.example.rescue4x4.more.weather.model.weather.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    // this adnotation are used to specify HTTP GET request method and the relative URL
    // the method returns a WeatherResult object

    /*
    https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}
    @Query e folosit pentru a adauga parametrii la URL transmis cu GET request, cum e si mai sus in
    exemplul de URL dat

    @GET e folosit pentru a arata ca facem un HTTP GET request in exemplul de mai sus fiind cu
    la 2.5/weather incolo

    also ... suspend functionul e o functie ce poate pune pauza de la executie si sa astepte si sa
    reia executia mai tarziu fara a bloca firul de executie deci aceasta functie poate fi apelata
    fara a bloca firul de executie
     */
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