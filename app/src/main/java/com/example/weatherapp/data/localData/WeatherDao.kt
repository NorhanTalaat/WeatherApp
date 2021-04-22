package com.example.weatherapp.data.localData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.Alarm
import com.example.weatherapp.model.WeatherResponse

@Dao
interface WeatherDao {

    @Query("SELECT * FROM Weather")
    fun getAll(): LiveData<List<WeatherResponse>>

    @Query("SELECT * FROM Weather")
    fun getAllData(): List<WeatherResponse>

    @Query("SELECT * FROM weather WHERE lat=:lat AND lon=:lng ")
    fun getWeatherResponse(lat:String,lng:String): LiveData<WeatherResponse>

    @Query("SELECT * FROM weather WHERE timezone =:timezone ")
    fun getCites(timezone:String?): LiveData<WeatherResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(weather: WeatherResponse)

    @Query("DELETE FROM Weather")
     fun deleteAll()

     @Query("DELETE FROM Weather WHERE timezone=:timezone ")
     fun deleteByTimezone(timezone: String?)

    @Query("SELECT * FROM Weather WHERE timezone =:timezone ")
    fun getTimezone(timezone:String?): WeatherResponse



    //////////////Alarm/////////////////

    @Query("SELECT * FROM Alarms")
    fun getAllAlarms(): LiveData<List<Alarm>>

    @Query("SELECT * FROM Alarms Where id = :id ")
    fun getApiObj(id:Int): Alarm

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmObj: Alarm):Long

    @Query("DELETE FROM Alarms WHERE id = :id")
    suspend fun deleteAlarmObj(id:Int)
}