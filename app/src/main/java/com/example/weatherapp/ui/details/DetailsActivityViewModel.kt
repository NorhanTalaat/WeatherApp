package com.example.weatherapp.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.model.WeatherResponse

class DetailsActivityViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: WeatherRepository = WeatherRepository(application)

    fun getDetails(timezone:String): LiveData<WeatherResponse> {
        return repository.getCites(timezone)
    }

}