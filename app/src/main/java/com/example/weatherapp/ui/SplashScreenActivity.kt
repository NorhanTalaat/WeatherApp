package com.example.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityCompat
import com.example.weatherapp.R
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.math.BigDecimal
import java.math.RoundingMode

class SplashScreenActivity: AppCompatActivity() {

    companion object {
        const val TAG = "Splash"
    }

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashMotion ()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        sharedPreferences = this.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        editor= sharedPreferences.edit()


    }

    fun splashMotion (){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            decorView.systemUiVisibility = uiOptions
        }


        motion_layout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
//                Log.i(TAG,"Start")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
//                Log.i(TAG,"Change")

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
//               Log.i(TAG,"Complete")

               // startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
                getLastLocation()
                editor.commit()
               // finish()
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
//                Log.i(TAG,"Triggered")
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        ///
                       Toast.makeText(this,
                     location.latitude.toString()+"+"+location.longitude.toString(), Toast.LENGTH_LONG).show()
                        val d1 : BigDecimal = BigDecimal(location.latitude).setScale(4, RoundingMode.HALF_DOWN)
                        val d2 : BigDecimal = BigDecimal(location.longitude).setScale(4, RoundingMode.HALF_DOWN)
                        editor.putString("lat","$d1")
                        editor.putString("lon","$d2")
                        editor.commit()


                        startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
                        finish()


                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()

        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

//            Toast.makeText(this@SplashScreenActivity,
//                mLastLocation.latitude.toString()+" + "+mLastLocation.longitude.toString(), Toast.LENGTH_LONG).show()

            startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
            finish()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

}