package com.example.rescue4x4.more.weather.model.weather

import com.google.gson.annotations.SerializedName

// each property in the Main data class represents a weather related atribute
// by using @SerializedName("") we specify the exact name of the JSON field that we want to map to the property
// Explicatie si in romana
// in arborele JSON exista un obiect numit "main" care contine mai multe campuri
// fiecare camp cum e si aici de ex temp, feels_like, temp_min, temp_max, pressure, humidity etc
// trebuie sa le definim pe toate bazat pe ce ne returneaza apiu sau documentatia apiului
// de exemplu in cazul obiectului clouds, in arborele JSON exista un obiect numit "clouds" care contine un camp numit "all"
// de aia in @SerializedName punem ("temp") pentru ca facem legatura cu temp iar variabila de obicei
// trebuie numita la fel ca si campul din JSON

data class Main(
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("temp_min") var tempMin: Double? = null,
    @SerializedName("temp_max") var tempMax: Double? = null,
    @SerializedName("pressure") var pressure: Double? = null,
    @SerializedName("humidity") var humidity: Double? = null
)