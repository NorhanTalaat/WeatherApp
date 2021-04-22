package com.example.weatherapp.ui.alarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.localData.DataSource
import com.example.weatherapp.model.Alarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    var localDataSource: DataSource
    val navegate = MutableLiveData<Alarm>()


    init {
        localDataSource = DataSource(application)
    }

    fun deleteAlarmObj(id: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.deleteAlarmObj(id)
        }
    }

    suspend fun insertAlarmObj(alarmObj: Alarm): Long {

        return localDataSource.insertAlarm(alarmObj)

    }


    fun onEditClick(alarmObj: Alarm) {

        navegate.value = alarmObj

    }

    fun getAlarmList(): LiveData<List<Alarm>> {
        return localDataSource.getAllAlarmObj()
    }


    fun getNavigate(): LiveData<Alarm> {
        return navegate
    }
}