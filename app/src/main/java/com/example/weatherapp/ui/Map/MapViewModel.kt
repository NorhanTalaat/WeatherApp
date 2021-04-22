package com.example.weatherapp.ui.Map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.model.WeatherResponse

class MapViewModel (application: Application) : AndroidViewModel(application) {

        private val repository: WeatherRepository = WeatherRepository(application)


        fun setData(lat:String,lon:String) {
            return repository.setDataByLocation(lat,lon)
        }

    }