package com.example.weatherapp.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.model.WeatherResponse

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

        private val repository: WeatherRepository = WeatherRepository(application)

        fun refresh() {
            return repository.refresh()
        }
    }