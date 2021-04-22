package com.example.weatherapp.ui.current

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R

import com.example.weatherapp.model.WeatherResponse
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.layout_additional_weather_info.*
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ui.*
import com.example.weatherapp.ui.current.futureWeather.DailyAdapter
import com.example.weatherapp.ui.current.futureWeather.FutureDetailWeatherFragment
import com.example.weatherapp.ui.unixTimestampToDateTimeString
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*

class CurrentWeatherFragment : Fragment() {

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var hourlyAdapter: HourlyAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var lang :String
    lateinit var unit :String
    lateinit var tempUnit :String
    lateinit var windSpeedUnit:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(CurrentWeatherViewModel::class.java)

        sharedPreferences = requireActivity().getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        editor= sharedPreferences.edit()
        lang = sharedPreferences.getString("lang", "en").toString()
        unit = sharedPreferences.getString("units", "metric").toString()
        setUnits(unit)

        hourlyAdapter= HourlyAdapter(arrayListOf(), requireActivity())
        initUI()

        viewModel.currentWeatherData().observe(viewLifecycleOwner,{
            if(it != null) {
                hourlyAdapter.updateDays(it.hourly)
                showData(it)
                editor.putString("timezone",it.timezone)
                editor.commit()
            }
        })

        btn_forecast.setOnClickListener(){
            openFragment()
        }
    }

    private fun initUI(){
        rv_hourly.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = hourlyAdapter
        }
    }

    fun openFragment(){
        val fragment = FutureDetailWeatherFragment()
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun showData(it: WeatherResponse){

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


