package com.example.weatherapp.data.remoteData

import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


val appId ="5028c5b091a5d121de6de7ad2b25df42"
interface WeatherApi {

    @GET("onecall")
    suspend fun getCurrentWeatherData(@Query("lat") lat: String,
                                      @Query("lon") lon: String,
                                      @Query("exclude") exclude: String ,
                                      @Query("units") units: String,
                                      @Query("lang") lang: String,
                                      @Query("appid") app_id: String= appId): Response<WeatherResponse>


}