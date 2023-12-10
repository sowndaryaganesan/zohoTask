package com.app.android.utils

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.app.android.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {


    companion object {
        fun getWeatherAnimation(weatherCode: Int): Int {
            if (weatherCode / 100 == 2) {
                return R.raw.storm_weather
            } else if (weatherCode / 100 == 3) {
                return R.raw.rainy_weather
            } else if (weatherCode / 100 == 5) {
                return R.raw.rainy_weather
            } else if (weatherCode / 100 == 6) {
                return R.raw.snow_weather
            } else if (weatherCode / 100 == 7) {
                return R.raw.unknown
            } else if (weatherCode == 800) {
                return R.raw.clear_day
            } else if (weatherCode == 801) {
                return R.raw.few_clouds
            } else if (weatherCode == 803) {
                return R.raw.broken_clouds
            } else if (weatherCode / 100 == 8) {
                return R.raw.cloudy_weather
            }
            return R.raw.unknown
        }

        fun setWeatherIcon(context: Context?, imageView: AppCompatImageView, weatherCode: Int) {
            if (weatherCode / 100 == 2) {
                Glide.with(context!!).load(R.drawable.ic_storm_weather).into(imageView)
            } else if (weatherCode / 100 == 3) {
                Glide.with(context!!).load(R.drawable.ic_rainy_weather).into(imageView)
            } else if (weatherCode / 100 == 5) {
                Glide.with(context!!).load(R.drawable.ic_rainy_weather).into(imageView)
            } else if (weatherCode / 100 == 6) {
                Glide.with(context!!).load(R.drawable.ic_snow_weather).into(imageView)
            } else if (weatherCode / 100 == 7) {
                Glide.with(context!!).load(R.drawable.ic_unknown).into(imageView)
            } else if (weatherCode == 800) {
                Glide.with(context!!).load(R.drawable.ic_clear_day).into(imageView)
            } else if (weatherCode == 801) {
                Glide.with(context!!).load(R.drawable.ic_few_clouds).into(imageView)
            } else if (weatherCode == 803) {
                Glide.with(context!!).load(R.drawable.ic_broken_clouds).into(imageView)
            } else if (weatherCode / 100 == 8) {
                Glide.with(context!!).load(R.drawable.ic_cloudy_weather).into(imageView)
            }
        }


        fun convertTime(dateTimeString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            val date = inputFormat.parse(dateTimeString)
            return outputFormat.format(date)
        }
    }




}