package com.example.weatherapp.ui.details

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.ui.*
import com.example.weatherapp.ui.current.HourlyAdapter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.current_weather_fragment.rv_hourly
import kotlinx.android.synthetic.main.current_weather_fragment.tv_city_name
import kotlinx.android.synthetic.main.current_weather_fragment.tv_date
import kotlinx.android.synthetic.main.current_weather_fragment.tv_temp
import kotlinx.android.synthetic.main.current_weather_fragment.tv_temp_degree
import kotlinx.android.synthetic.main.current_weather_fragment.tv_weather_condition
import kotlinx.android.synthetic.main.layout_additional_weather_info.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsActivityViewModel
    private lateinit var hourlyAdapter: HourlyAdapter
    private lateinit var dailyAdapter: DetailsDailyAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var lang :String
    lateinit var unit :String
    lateinit var tempUnit :String
    lateinit var windSpeedUnit:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(DetailsActivityViewModel::class.java)

        sharedPreferences = this.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        editor= sharedPreferences.edit()
        lang = sharedPreferences.getString("lang", "en").toString()
        unit = sharedPreferences.getString("units", "metric").toString()
        setUnits(unit)
        val location : String? = intent.getStringExtra("timezone")

        hourlyAdapter= HourlyAdapter(arrayListOf(), this)
        dailyAdapter= DetailsDailyAdapter(arrayListOf(), this)
        initUI()

        if (location != null) {
            viewModel.getDetails(location).observe(this,{

                if(it != null) {
                    hourlyAdapter.updateDays(it.hourly)
                    dailyAdapter.updateDays(it.daily)
                    showData(it)
                }
            })
        }
    }
    private fun initUI(){
        rv_hourly.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = hourlyAdapter
        }
        rv_daily.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = dailyAdapter
        }
    }

    fun showData(it: WeatherResponse) {
        if(lang.equals("en")) {
            tv_temp.text = it.current?.temp.toString()
            tv_city_name.text = it.timezone
            it.current?.let { it1 ->
                tv_temp_degree.text = tempUnit
                tv_weather_condition.text = it1.weather?.get(0)?.description
                tv_date.text = it1.dt.uDateTimeString("en")
                tv_sunrise_time.text = it1.sunrise.uTimeString("en")
                tv_sunset_time.text = it1.sunset.uTimeString("en")
                tv_cloudiness_text.text = "${it1.clouds}%"
                tv_pressure_text.text = "${it1.pressure}hPa"
                tv_visibility_text.text = "${it1.visibility}M"
                tv_humidity_text.text = "${it1.humidity}%"
                tv_real_feel_text.text = "${it1.feels_like}$tempUnit"
                tv_wind_speed_text.text = "${it1.wind_speed}$windSpeedUnit"
            }
        }else {
            tv_temp.text = convertToArabic(it.current?.temp?.toInt())
            tv_city_name.text = it.timezone
            it.current?.let { it1 ->
                tv_temp_degree.text = tempUnit
                tv_weather_condition.text = it1.weather?.get(0)?.description
                tv_date.text = it1.dt.uDateTimeString("ar")
                tv_sunrise_time.text = it1.sunrise.uTimeString("ar")
                tv_sunset_time.text = it1.sunset.uTimeString("ar")
                tv_cloudiness_text.text = convertToArabic(it1.clouds.toInt())+"%"
                tv_pressure_text.text = convertToArabic(it1.pressure.toInt()*100) +"بسكال"
                tv_visibility_text.text = convertToArabic(it1.visibility.toInt())+"م"
                tv_humidity_text.text = convertToArabic(it1.humidity.toInt())+"%"
                tv_real_feel_text.text = convertToArabic(it1.feels_like.toInt())+tempUnit
                tv_wind_speed_text.text = convertToArabic(it1.wind_speed.toInt())+windSpeedUnit

            }
        }
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