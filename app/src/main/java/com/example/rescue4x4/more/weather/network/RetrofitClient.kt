package com.example.rescue4x4.more.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*

 Retrofit is a type-safe HTTP client for Android and Java applications.
 It simplifies the process of making HTTP requests to web services and APIs by providing
  a high-level interface for defining RESTful API endpoints and handling network communication.

  so basically retrofit simplifies the process of making network requests in android
  it abstracts away the boilerplate code of making network requests and parsing the response
 */
class RetrofitClient {
    companion object{

        private var apiService: IApiService? = null
        fun getInstance(): IApiService {
            if(apiService == null){
                // if apiService is null then create a new instance of Retrofit
                apiService = Retrofit.Builder()
                    // set the base url ( de aici porneste aia cu query-ul)
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    // add a gson converter factory to handle json serialization and deserialization
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IApiService::class.java)

            }
            return apiService!!
        }
    }
}