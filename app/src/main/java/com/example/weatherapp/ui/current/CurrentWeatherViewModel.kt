package com.example.weatherapp.ui.current

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.model.WeatherResponse

class CurrentWeatherViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: WeatherRepository = WeatherRepository(application)

    fun currentWeatherData():LiveData<WeatherResponse>{
        return repository.fetchData()
    }
}