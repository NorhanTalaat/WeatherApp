package com.example.weatherapp.ui.current.futureWeather

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.model.Daily
import com.example.weatherapp.ui.*
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class DailyAdapter(private val dailyList: ArrayList<Daily>, activity: Activity)  : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>()  {

    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor
    var lang :String
    var unit :String
    lateinit var tempUnit :String
    lateinit var windSpeedUnit:String

    init{
        sharedPreferences = activity.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        editor= sharedPreferences.edit()
        lang = sharedPreferences.getString("lang", "en").toString()
        unit = sharedPreferences.getString("units", "metric").toString()

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): DailyAdapter.DailyViewHolder {
        return  DailyViewHolder(ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }

    override fun onBindViewHolder(holder: DailyAdapter.DailyViewHolder, position: Int) {
        setUnits(unit)

        Picasso.get().load(iconLinkgetter(dailyList[position].weather.get(0).icon)).into(holder.binding.ivWeatherIcon)
        holder.binding.tvWeatherCondition.text = dailyList[position].weather[0].description

        if(lang.equals("en")) {

            holder.binding.cvDayForecast.tvDayTemp.text = "${dailyList[position].temp.day}$tempUnit"
            holder.binding.cvDayForecast.tvEveTemp.text = "${dailyList[position].temp.eve}$tempUnit"
            holder.binding.cvDayForecast.tvNightTemp.text = "${dailyList[position].temp.night}$tempUnit"
            holder.binding.cvDayForecast.tvMaxTemp.text = "${dailyList[position].temp.max}$tempUnit"
            holder.binding.cvDayForecast.tvMinTemp.text = "${dailyList[position].temp.min}$tempUnit"

            holder.binding.cvDayForecast.tvMornFeel.text = "${dailyList[position].feels_like.morn}$tempUnit"
            holder.binding.cvDayForecast.tvDayFeel.text = "${dailyList[position].feels_like.day}$tempUnit"
            holder.binding.cvDayForecast.tvEveFeel.text = "${dailyList[position].feels_like.eve}$tempUnit"
            holder.binding.cvDayForecast.tvNightFeel.text = "${dailyList[position].feels_like.night}$tempUnit"
            holder.binding.tvTimeForecast.text = dailyList[position].dt.unixTimestampToDateTimeString()
            holder.binding.cvDayForecast.tvSunriseTime.text = dailyList[position].sunrise.unixTimestampToTimeString()
            holder.binding.cvDayForecast.tvSunsetTime.text = dailyList[position].sunset.unixTimestampToTimeString()
        }else
            {
            holder.binding.cvDayForecast.tvDayTemp.text = convertToArabic(dailyList[position].temp.day.toInt())+tempUnit
            holder.binding.cvDayForecast.tvEveTemp.text = convertToArabic(dailyList[position].temp.eve.toInt())+tempUnit
            holder.binding.cvDayForecast.tvNightTemp.text = convertToArabic(dailyList[position].temp.night.toInt())+tempUnit
            holder.binding.cvDayForecast.tvMaxTemp.text = convertToArabic(dailyList[position].temp.max.toInt())+tempUnit
            holder.binding.cvDayForecast.tvMinTemp.text = convertToArabic(dailyList[position].temp.min.toInt())+tempUnit

            holder.binding.cvDayForecast.tvMornFeel.text = convertToArabic(dailyList[position].feels_like.morn.toInt())+tempUnit
            holder.binding.cvDayForecast.tvDayFeel.text = convertToArabic(dailyList[position].feels_like.day.toInt())+tempUnit
            holder.binding.cvDayForecast.tvEveFeel.text = convertToArabic(dailyList[position].feels_like.eve.toInt())+tempUnit
            holder.binding.cvDayForecast.tvNightFeel.text = convertToArabic(dailyList[position].feels_like.night.toInt())+tempUnit
            holder.binding.tvTimeForecast.text = dailyList[position].dt.unixTimestampToDateTimeString()
            holder.binding.cvDayForecast.tvSunriseTime.text = dailyList[position].sunrise.unixTimestampToTimeString()
            holder.binding.cvDayForecast.tvSunsetTime.text = dailyList[position].sunset.unixTimestampToTimeString()
        }

    }



    fun updateDays(newDailyList: List<Daily>) {
        dailyList.clear()
        dailyList.addAll(newDailyList)
        notifyDataSetChanged()
    }
    inner class DailyViewHolder constructor(val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setUnits(unit:String){
        when(unit){
            "metric" -> {
                if(lang.equals("en")) {
                    tempUnit = "°c"
                    windSpeedUnit = "m/s"
                }else{
                    tempUnit = "°م"
                    windSpeedUnit = "م/ث"
                }
            }
            "imperial"-> {
                if(lang.equals("en")) {
                    tempUnit = "°f"
                    windSpeedUnit = "m/h"
                }else{
                    tempUnit = "°ف"
                    windSpeedUnit = "م/س"
                }
            }
            "standard"-> {
                if(lang.equals("en")) {
                    tempUnit = "°k"
                    windSpeedUnit = "m/s"
                }else{
                    tempUnit = "°ك"
                    windSpeedUnit = "م/ث"
                }
            }
        }
    }
}
