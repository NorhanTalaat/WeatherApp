package com.example.weatherapp.ui.details

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.DailyItemBinding
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.Hourly
import com.example.weatherapp.ui.*
import com.example.weatherapp.ui.current.HourlyAdapter
import com.example.weatherapp.ui.unixTimestampToDateTimeString
import com.squareup.picasso.Picasso

class DetailsDailyAdapter (private val dailyList: ArrayList<Daily>, activity: Activity)
    : RecyclerView.Adapter<DetailsDailyAdapter.DailyViewHolder>()  {

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

    ): DetailsDailyAdapter.DailyViewHolder {
        return  DailyViewHolder(DailyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class DailyViewHolder constructor(val binding: DailyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: DetailsDailyAdapter.DailyViewHolder, position: Int) {
        setUnits(unit)

        Picasso.get().load(iconLinkgetter(dailyList[position].weather.get(0).icon)).into(holder.binding.ivAddCity)
        if(lang.equals("en")){
            holder.binding.tvDate.text = dailyList[position].dt.uDateTimeString("en")
            holder.binding.tvTempDaily.text = dailyList[position].temp.day.toString() +tempUnit
            holder.binding.tvDescription.text= dailyList[position].weather[0].description
        }else{

            holder.binding.tvDate.text = dailyList[position].dt.uDateTimeString("ar")
            holder.binding.tvTempDaily.text = convertToArabic(dailyList[position].temp.day.toInt()) +tempUnit
            holder.binding.tvDescription.text= dailyList[position].weather[0].description
        }
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }
    fun updateDays(newDailyList: List<Daily>) {
        dailyList.clear()
        dailyList.addAll(newDailyList)
        notifyDataSetChanged()
    }

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