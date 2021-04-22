package com.example.weatherapp.ui.current

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.HourlyItemBinding
import com.example.weatherapp.model.Hourly
import com.example.weatherapp.ui.convertToArabic
import com.example.weatherapp.ui.iconLinkgetter
import com.example.weatherapp.ui.uTimeString
import com.example.weatherapp.ui.unixTimestampToTimeString
import com.squareup.picasso.Picasso

class HourlyAdapter(private val hourlyList: ArrayList<Hourly>, activity: Activity)
    : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>()  {

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

    ): HourlyAdapter.HourlyViewHolder {
        return  HourlyViewHolder(HourlyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class HourlyViewHolder constructor(val binding: HourlyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: HourlyAdapter.HourlyViewHolder, position: Int) {
        setUnits(unit)

        Picasso.get().load(iconLinkgetter(hourlyList[position].weather.get(0).icon)).into(holder.binding.ivAddCity)
        if(lang.equals("en")){
            holder.binding.tvHour.text = hourlyList[position].dt.toInt().uTimeString("en")
            holder.binding.tvTemp.text = hourlyList[position].temp.toString() +tempUnit
        }else{
            holder.binding.tvHour.text =hourlyList[position].dt.toInt().uTimeString("ar")
            holder.binding.tvTemp.text = convertToArabic(hourlyList[position].temp.toInt())+tempUnit
        }
    }

    override fun getItemCount(): Int {
        return hourlyList.size
    }
    fun updateDays(newHourlyList: List<Hourly>) {
        hourlyList.clear()
        hourlyList.addAll(newHourlyList)
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