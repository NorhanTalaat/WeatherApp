package com.example.weatherapp.data.remoteData

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun getCurrentData() : WeatherApi {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApi::class.java)    }
}