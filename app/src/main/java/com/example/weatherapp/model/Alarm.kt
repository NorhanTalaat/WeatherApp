package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alarms")
data class Alarm (
    @ColumnInfo(name = "event")
    var event : String,
    var Date : String,
    var start : String,
    var end : String,
    var sound:Boolean,
    var description : String

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}