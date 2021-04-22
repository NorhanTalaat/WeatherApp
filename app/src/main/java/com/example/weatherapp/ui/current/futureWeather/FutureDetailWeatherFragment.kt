package com.example.weatherapp.ui.current.futureWeather

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*

class FutureDetailWeatherFragment (): Fragment() {

    private lateinit var viewModel: FutureDetailWeatherViewModel
    private lateinit var dailyAdapter: DailyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(FutureDetailWeatherViewModel::class.java)

        // TODO: Use the ViewModel
        dailyAdapter= DailyAdapter(arrayListOf(), requireActivity())
        initUI()

        viewModel.currentWeatherData().observe(viewLifecycleOwner,{
            dailyAdapter.updateDays(it.daily)
        })
    }

    private fun initUI(){
        rv_forecast.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = dailyAdapter
        }
    }

}