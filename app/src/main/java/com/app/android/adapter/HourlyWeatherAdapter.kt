package com.app.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.android.databinding.ItemHourWeatherBinding
import com.app.android.model.HourlyWeather
import com.app.android.utils.AppUtils

class HourlyWeatherAdapter(private val context : Context,private val hourlyWeatherList: List<HourlyWeather>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        val binding = ItemHourWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hourlyWeather = hourlyWeatherList[position]

        AppUtils.setWeatherIcon(context, holder.weatherIcon, hourlyWeather.weatherIcon)
        holder.hourTextView.text = hourlyWeather.hour
        holder.temperatureTextView.text = hourlyWeather.temperature.toString() +"°C"
    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.size
    }



    class MyViewHolder(binding: ItemHourWeatherBinding) : RecyclerView.ViewHolder(binding.root) {


        var weatherIcon = binding.weatherIcon
        var hourTextView = binding.hourTextView
        var temperatureTextView = binding.temperatureTextView


    }

}