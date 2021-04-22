package com.example.weatherapp.ui.alarm

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.AlarmItemBinding
import com.example.weatherapp.model.Alarm
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.utils.myAlarmReceiver

class AlarmAdapter(
    var alarmList: ArrayList<Alarm>,
    alartViewModel: AlarmViewModel,
    context: Context
) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    var context: Context
    var alartViewModel: AlarmViewModel

    init {
        this.context = context
        this.alartViewModel = alartViewModel
    }


    fun updateAlarms(newAlarmList: List<Alarm>) {
        alarmList.clear()
        alarmList.addAll(newAlarmList)
        notifyDataSetChanged()
    }

    class ViewHolder(var myView: AlarmItemBinding) : RecyclerView.ViewHolder(myView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            AlarmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myView.alarmType.text = alarmList[position].event
        holder.myView.dateTime.text =
            alarmList[position].Date + "\n" + alarmList[position].start + " to " + alarmList[position].end
        holder.myView.details.text = alarmList[position].description

        holder.myView.editBtn.setOnClickListener {
            alartViewModel.onEditClick(alarmList[position])
        }
    }

    fun getItemAt(pos: Int) = alarmList.get(pos)
    override fun getItemCount() = alarmList.size

}