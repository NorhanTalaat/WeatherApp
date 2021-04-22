package com.example.weatherapp.data.localData

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.model.Alarm
import com.example.weatherapp.model.WeatherResponse

@Database(entities = [WeatherResponse::class, Alarm::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
 abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    companion object {

        private var INSTANCE: WeatherDataBase? = null
        fun getDatabase(application: Application): WeatherDataBase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                 application.applicationContext,
                    WeatherDataBase::class.java,
                    "weather_database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}