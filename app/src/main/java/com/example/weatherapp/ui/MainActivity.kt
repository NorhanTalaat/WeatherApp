package com.example.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.ui.alarm.AlarmFragment
import com.example.weatherapp.ui.current.CurrentWeatherFragment
import com.example.weatherapp.ui.current.futureWeather.FutureDetailWeatherFragment
import com.example.weatherapp.ui.favourite.FavouirateFragment
import com.example.weatherapp.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class MainActivity (): AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var lang :String
    lateinit var unit :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        editor= sharedPreferences.edit()
        firstTime()
        lang = sharedPreferences.getString("lang", "en").toString()
        unit = sharedPreferences.getString("units", "metric").toString()
        setLocalLanguage(lang)

        val currentWeatherFragment =CurrentWeatherFragment()
        val favouirateFragment =FavouirateFragment()
        val settingsFragment =SettingsFragment()
        val alarmFragment = AlarmFragment()

        makeCurrentFragment(currentWeatherFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.currentWeatherFragment ->makeCurrentFragment(currentWeatherFragment)
                R.id.favouirateFragment ->makeCurrentFragment(favouirateFragment)
                R.id.settingsFragment ->makeCurrentFragment(settingsFragment)
                R.id.alarmFragment->makeCurrentFragment(alarmFragment)

            }
            true
        }


    }
    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }

    fun setLocalLanguage(language :String?){
        val local = Locale(language)
        Locale.setDefault(local)
        val resources: Resources = this.resources
        val configuration: Configuration = resources.configuration
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(local)
        }
        resources.updateConfiguration(configuration,resources.displayMetrics)
    }
    private fun firstTime(){
        var isfirstTime :Boolean =sharedPreferences.getBoolean("first", true)
        if(isfirstTime){
            editor.putString("lang","en")
            editor.putString("units", "metric")
            editor.putBoolean("first",false)
            editor.commit()
        }
    }


}
