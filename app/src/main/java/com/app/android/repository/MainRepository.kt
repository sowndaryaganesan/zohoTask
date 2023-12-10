package com.app.android.repository

import com.app.android.api.RetrofitService
import com.app.android.api.WeatherRetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService,private val weatherRetrofitService: WeatherRetrofitService) {

    fun getNewsList() = retrofitService.getNewsList()

    fun getWeather(lat : Double, lon : Double, appid : String, units : String) = weatherRetrofitService.getWeather(lat,lon,appid,units)

    fun getAirQuality(lat : Double, lon : Double, appid : String, units : String) = weatherRetrofitService.getAirQuality(lat,lon,appid,units)

    fun getForecast(lat : Double, lon : Double, appid : String, units : String) = weatherRetrofitService.getForecast(lat,lon,appid,units)

}