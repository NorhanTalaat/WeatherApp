package com.example.weatherapp.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.SettingsFragmentBinding
import com.example.weatherapp.ui.MainActivity
import java.util.*

class SettingsFragment :Fragment(){

    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding :SettingsFragmentBinding
    lateinit var editor: SharedPreferences.Editor
    lateinit var viewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= SettingsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )
            .get(SettingsViewModel::class.java)

        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val lang: String? = sharedPreferences.getString("lang","en")
        val units: String? = sharedPreferences.getString("units","metric")

        when(lang){
            "en" -> binding.settingItem.btnEnglish.isChecked =true
            "ar" -> binding.settingItem.btnArabic.isChecked  =true
        }
        when(units){
            "metric"   -> binding.settingItem.btnMetric.isChecked   =true
            "imperial" -> binding.settingItem.btnImperial.isChecked =true
            "standard" -> binding.settingItem.btnStandard.isChecked =true
        }
        binding.settingItem.btnEnglish.setOnClickListener(View.OnClickListener {
            setLanguage("en")
        })
        binding.settingItem.btnArabic.setOnClickListener(View.OnClickListener {
            setLanguage("ar")
        })
        binding.settingItem.btnMetric.setOnClickListener(View.OnClickListener {
            setUnits("metric")
        })
        binding.settingItem.btnImperial.setOnClickListener(View.OnClickListener {
            setUnits("imperial")
        })
        binding.settingItem.btnStandard.setOnClickListener(View.OnClickListener {
            setUnits("standard")
        })


    }

    private fun setLanguage(language:String){
        editor.putString("lang",language)
        editor.commit()
        setLocalLanguage(language)
        viewModel.refresh()
        requireActivity().recreate()
        restartApp()
    }
    private fun setUnits(units:String){
        editor.putString("units",units)
        editor.commit()
        viewModel.refresh()
        requireActivity().recreate()
        //restartApp()
    }

    fun setLocalLanguage(language :String){
        val local = Locale(language)
        Locale.setDefault(local)
        val resources: Resources = requireContext().resources
        val configuration: Configuration = resources.configuration
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(local)
        }
        resources.updateConfiguration(configuration,resources.displayMetrics)
    }
    private fun restartApp(){
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )
        startActivity(intent)
        Runtime.getRuntime().exit(0)
    }


}