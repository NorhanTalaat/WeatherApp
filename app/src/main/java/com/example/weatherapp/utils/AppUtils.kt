package com.example.weatherapp.ui

import java.text.SimpleDateFormat
import java.util.*



fun Int.unixTimestampToDateTimeString() : String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this*1000.toLong()

        val outputDateFormat = SimpleDateFormat("dd MMM, yyyy - hh:mm a")
        outputDateFormat.timeZone = TimeZone.getDefault() // user's default time zone
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}

fun Int.unixTimestampToTimeString() : String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this*1000.toLong()

        val outputDateFormat = SimpleDateFormat("hh:mm a")
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}
fun Int.uDateTimeString(language:String) : String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this*1000.toLong()

        val outputDateFormat = SimpleDateFormat("dd MMM, yyyy - hh:mm a",Locale(language))
        outputDateFormat.timeZone = TimeZone.getDefault() // user's default time zone
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}

fun Int.uTimeString(language:String) : String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this*1000.toLong()

        val outputDateFormat = SimpleDateFormat("hh:mm a",Locale(language))
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}



fun iconLinkgetter(iconName:String):String="https://openweathermap.org/img/wn/"+iconName+"@2x.png"

fun convertToArabic(value: Int?): String? {
    return (value.toString() + "")
        .replace("1", "١").replace("2", "٢")
        .replace("3", "٣").replace("4", "٤")
        .replace("5", "٥").replace("6", "٦")
        .replace("7", "٧").replace("8", "٨")
        .replace("9", "٩").replace("0", "٠")

}