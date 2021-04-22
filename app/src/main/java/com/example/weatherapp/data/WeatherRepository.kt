package com.example.weatherapp.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.localData.DataSource
import com.example.weatherapp.data.remoteData.NetworkService
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.*
import retrofit2.Response

class WeatherRepository{
    var localDataSource: DataSource
    var remoteDataSource: NetworkService
    var sharedPreferences: SharedPreferences
    lateinit var lat :String
    lateinit var lon :String
    var lang :String
    var units:String

    constructor(application: Application) {

        localDataSource = DataSource(application)
        remoteDataSource=NetworkService
        sharedPreferences = application.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        lang = sharedPreferences.getString("lang","en").toString()
        units= sharedPreferences.getString("units","metric").toString()
    }

fun fetchData() : LiveData<WeatherResponse> {
    lat = sharedPreferences.getString("lat","0").toString()
    lon= sharedPreferences.getString("lon","0").toString()

    val exceptionHandlerException = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()

    }
    CoroutineScope(Dispatchers.IO + exceptionHandlerException).launch {
        val response = NetworkService.getCurrentData().getCurrentWeatherData(lat,lon,"minutely",units,lang)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                localDataSource.insert(response.body())

            }
        }
    }
    return localDataSource.getWeather(lat,lon)
}

    fun setDataByLocation(lat:String,lon:String) {

        val exceptionHandlerException = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()
            Log.i("id","exception")
        }
        CoroutineScope(Dispatchers.IO + exceptionHandlerException).launch {
            val response:Response<WeatherResponse> = NetworkService.getCurrentData()
                .getCurrentWeatherData(lat,lon,"minutely",units,lang)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    localDataSource.insert(response.body())
                }
            }
        }
    }

    fun getCites(timezone:String?) :LiveData<WeatherResponse>{
            val exceptionHandlerException = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()
                Log.i("id","exception")
            }
            CoroutineScope(Dispatchers.Main + exceptionHandlerException).launch {

                val weatherResponse = localDataSource.getCites(timezone)
                lat = weatherResponse.value?.lat.toString()
                lon = weatherResponse.value?.lon.toString()
                withContext(Dispatchers.IO) {
                    val response = NetworkService.getCurrentData()
                        .getCurrentWeatherData(lat, lon, "minutely", units, lang)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            localDataSource.insert(response.body())
                        }
                    }
                }
            }

            return localDataSource.getCites(timezone)
    }

    fun getAllData(): LiveData<List<WeatherResponse>> {
        return localDataSource.getAllWeather()
    }

    fun deleteTimezone(timezone:String?){
        val exceptionHandlerException = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()
            Log.i("id","exception")
        }
        CoroutineScope(Dispatchers.Main + exceptionHandlerException).launch {

            localDataSource.deleteByTimezone(timezone)

        }
    }
    fun refresh(){
        val exceptionHandlerException = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()
            Log.i("id","exception")
        }
        CoroutineScope(Dispatchers.Main + exceptionHandlerException).launch {
            val items :List<WeatherResponse> = localDataSource.getAll()
            Log.i("id", sharedPreferences.getString("lang","en").toString())
           for(item :WeatherResponse in items){
               setDataByLocation(item.lat.toString(),item.lon.toString())
           }

        }

    }

}