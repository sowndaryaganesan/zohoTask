package com.app.android.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.android.model.HoursWeatherResponse
import com.app.android.model.NewsListResponse
import com.app.android.model.WeatherResponse
import com.app.android.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val newsList = MutableLiveData<NewsListResponse>()
    val weatherList = MutableLiveData<WeatherResponse>()
    val airPollutionList = MutableLiveData<WeatherResponse>()
    val forecastList = MutableLiveData<HoursWeatherResponse>()
    val errorMessage = MutableLiveData<String>()

    fun getNewsList() {

        val response = mainRepository.getNewsList()
        response.enqueue(object : Callback<NewsListResponse> {
            override fun onResponse(call: Call<NewsListResponse>, response: Response<NewsListResponse>) {
                newsList.postValue(response.body())
            }

            override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getWeather(lat : Double, lon : Double, appid : String, units : String) {

        val response = mainRepository.getWeather(lat,lon,appid,units)
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
//                Log.d(ContentValues.TAG, "weatherList: $response.body()")
                weatherList.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getAirQuality(lat : Double, lon : Double, appid : String, units : String) {

        val response = mainRepository.getAirQuality(lat,lon,appid,units)
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                airPollutionList.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getForecast(lat : Double, lon : Double, appid : String, units : String) {

        val response = mainRepository.getForecast(lat,lon,appid,units)
        response.enqueue(object : Callback<HoursWeatherResponse> {
            override fun onResponse(call: Call<HoursWeatherResponse>, response: Response<HoursWeatherResponse>) {
                forecastList.postValue(response.body())
            }

            override fun onFailure(call: Call<HoursWeatherResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}