package com.example.weatherapp.ui.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.model.WeatherResponse

class FavouirateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository = WeatherRepository(application)

    fun getTimezone(): LiveData<List<WeatherResponse>> {
        return repository.getAllData()
    }

    fun deleteData(timezone :String){
        return repository.deleteTimezone(timezone)
    }
}