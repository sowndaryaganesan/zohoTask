package com.app.android.model

data class HoursWeatherResponse(
    val cod: Int,
    val message: String,
    val list: ArrayList<WeatherResponse>,
    val city: City
){
    data class City(
        val id: Int,
        val country: String,

    )
}