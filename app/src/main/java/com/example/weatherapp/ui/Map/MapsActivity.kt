package com.example.weatherapp.ui.Map

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.ui.current.futureWeather.FutureDetailWeatherViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var mapViewModel: MapViewModel
    lateinit var lat :String
    lateinit var lon :String
    var marker : Marker?=null
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        sharedPreferences = application.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        lat = sharedPreferences.getString("lat","0").toString()
        lon= sharedPreferences.getString("lon","0").toString()

        mapViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MapViewModel::class.java)

//        mapViewModel.setData(lat,lon)
//
//        finish()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val location = LatLng(lat.toDouble(),lon.toDouble())
        var sydney: LatLng=location
        var markerOptions = MarkerOptions()




        markerOptions.title("Location is Selected")
        markerOptions.position(location)

        marker= mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        // Add a marker in Sydney and move the camera
        mMap.setOnMapClickListener { point ->

            Toast.makeText(this,
                point.latitude.toString()+" + "+point.longitude.toString(), Toast.LENGTH_LONG).show()
            lat = point.latitude.toString()
            lon= point.latitude.toString()
            sydney = LatLng(point.latitude , point.longitude)

            marker!!.position = sydney
            marker!!.rotation= maxOf(0.5f,0.5f)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,20f))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


            mapViewModel.setData(point.latitude.toString(),point.longitude.toString())
            finish()

        }
    }
}