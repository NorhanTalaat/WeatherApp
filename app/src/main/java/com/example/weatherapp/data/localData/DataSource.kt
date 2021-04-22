package com.example.weatherapp.data.localData

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.weatherapp.model.Alarm
import com.example.weatherapp.model.WeatherResponse


class DataSource(application: Application) {
    var weatherDao:WeatherDao = WeatherDataBase.getDatabase(application).weatherDao()


    fun getAllWeather(): LiveData<List<WeatherResponse>> {
        return weatherDao.getAll()
    }
    fun getAll():List<WeatherResponse> {
        return weatherDao.getAllData()
    }
    fun getWeather(lat:String,lng:String): LiveData<WeatherResponse> {
        return weatherDao.getWeatherResponse(lat,lng)
    }
    fun getCites(timezone:String?): LiveData<WeatherResponse> {
        return weatherDao.getCites(timezone)
    }
    fun deleteByTimezone (timezone: String?) {
        return weatherDao.deleteByTimezone(timezone)
    }
    fun insert(weather: WeatherResponse?) {
        weather?.let { weatherDao.insert(it) }
    }

    fun getTimezone(timezone:String?): WeatherResponse {
        return weatherDao.getTimezone(timezone)
    }

    //////////////Alarm///////////

    suspend fun deleteAlarmObj(id: Int) {
        weatherDao.deleteAlarmObj(id)
    }

    fun getAllAlarmObj(): LiveData<List<Alarm>> {
        return weatherDao.getAllAlarms()
    }

    suspend fun insertAlarm(alarmObj: Alarm): Long {
        return weatherDao.insertAlarm(alarmObj)
    }
}