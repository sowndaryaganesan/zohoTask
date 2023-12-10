package com.app.android.fragment

import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.android.LocationListener
import com.app.android.LocationService
import com.app.android.adapter.AirQualityAdapter
import com.app.android.adapter.HourlyWeatherAdapter
import com.app.android.api.RetrofitService
import com.app.android.api.WeatherRetrofitService
import com.app.android.databinding.FragmentWeatherBinding
import com.app.android.model.Components
import com.app.android.model.HourlyWeather
import com.app.android.repository.MainRepository
import com.app.android.utils.AppUtils
import com.app.android.utils.PermissionUtils
import com.app.android.viewModel.MainViewModel
import com.app.android.viewModel.ViewModelFactory
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.json.JSONObject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class WeatherFragment : Fragment(), LocationListener {

    private lateinit var binding: FragmentWeatherBinding

    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var locationService: LocationService
    private lateinit var weatherViewModel: MainViewModel

    private lateinit var airQualityAdapter : AirQualityAdapter

    private val retrofitService = RetrofitService.getInstance()
    private val weatherRetrofitService = WeatherRetrofitService.getInstance()

    private var hourlyWeatherList : List<HourlyWeather> = emptyList()

    private var latitude : Double = 0.0
    private var longitude : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        initView()

        return binding.root
    }

    private fun initView(){
        locationService = context?.let { LocationService(it, this) }!!
        checkLocationPermission()
        binding.progressBar.visibility = View.VISIBLE
        //get viewModel instance using ViewModelProvider.Factory
        weatherViewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService,weatherRetrofitService))).get(MainViewModel::class.java)


        Log.d("latitude", latitude.toString())
    }


    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude


        weatherViewModel.getWeather(latitude,longitude,"97a292de1a727652b55e4c903ae05e29", "metric")
        weatherViewModel.getAirQuality(latitude,longitude,"97a292de1a727652b55e4c903ae05e29", "metric")
        weatherViewModel.getForecast(latitude,longitude,"97a292de1a727652b55e4c903ae05e29", "metric")
        getAirQuality()
        getWeatherResponse()
        getHoursForeCast()
        binding.airQuality.text = "Air Quality Index"
        binding.hours.text = "24-hours forecast"
        binding.progressBar.visibility = View.GONE

    }


    private fun getWeatherResponse(){
        weatherViewModel.weatherList.observe(viewLifecycleOwner) {

            if (it != null) {
                val roundedValue = it.main.temp.roundToInt()
                binding.tempText.text = "$roundedValue\u00B0"
                binding.humidity.text = "Humidity " + it.main.humidity.toString() +"%"
                binding.wind.text = "Wind " + it.wind.speed +"km/h"
                binding.animationView.setAnimation(AppUtils.getWeatherAnimation(it.weather.get(0).id))
                binding.animationView.playAnimation()

            }

        }

        weatherViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.d(ContentValues.TAG, "errorMessage: $it")
        }
    }

    private fun getHoursForeCast(){

        weatherViewModel.forecastList.observe(viewLifecycleOwner) { forecast ->
            if (forecast != null) {
                 hourlyWeatherList = forecast.list.map { apiHourlyData ->
                    HourlyWeather(
                        hour = AppUtils.convertTime(apiHourlyData.dt_txt),
                        temperature = apiHourlyData.main.temp.roundToInt(),
                        weatherIcon = apiHourlyData.weather.get(0).id
                    )


                }
                binding.hourlyRecyclerView.adapter = HourlyWeatherAdapter(requireContext(),hourlyWeatherList)
                setupLineChart(hourlyWeatherList)

            }
        }

        //invoked when a network exception occurred
        weatherViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.d(ContentValues.TAG, "errorMessage: $it")
        }
    }

    private fun getAirQuality(){

        weatherViewModel.airPollutionList.observe(viewLifecycleOwner) {

            if (it != null) {

                Log.d(ContentValues.TAG, "weatherList: $it")
                binding.aqi.text = "AQI " + it.list.get(0).main.aqi.toString()

                val componentsString = """
    {
        "co": ${it.list[0].components.co},
        "no": ${it.list[0].components.no},
        "no2": ${it.list[0].components.no2},
        "o3": ${it.list[0].components.o3},
        "so2": ${it.list[0].components.so2},
        "pm2_5": ${it.list[0].components.pm2_5},
        "pm10": ${it.list[0].components.pm10},
        "nh3": ${it.list[0].components.nh3}
    }
""".trimIndent()

                val componentsObject = JSONObject(componentsString)
                val componentList = ArrayList<Components>()

                for (key in componentsObject.keys()) {
                    val value = componentsObject.getDouble(key)
                    val component = Components(key, value)
                    componentList.add(component)
                }
                airQualityAdapter = AirQualityAdapter()
                airQualityAdapter.setAirQualityList(componentList)
                binding.airRecyclerview.adapter = airQualityAdapter
            }

        }

        weatherViewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.d(ContentValues.TAG, "errorMessage: $it")
        }
    }




    private fun setupLineChart(hourlyWeatherList: List<HourlyWeather>) {
        // Clear existing data
        binding.lineChart.clear()

        val entries = mutableListOf<Entry>()

        hourlyWeatherList.forEachIndexed { index, data ->
            entries.add(Entry(index.toFloat(), data.temperature.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        val dataSet = LineData(lineDataSet)
        binding.lineChart.data = dataSet

        binding.lineChart.xAxis.valueFormatter = XAxisFormatter(hourlyWeatherList.map { it.hour }.toTypedArray())
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.lineChart.xAxis.labelRotationAngle = -45f
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.axisLeft.isEnabled = false
        binding.lineChart.description.isEnabled = false


        val visibleXRange = 6f
        binding.lineChart.setVisibleXRangeMaximum(visibleXRange)


        binding.lineChart.xAxis.axisMinimum = 0f
        binding.lineChart.xAxis.axisMaximum = hourlyWeatherList.size.toFloat()

        binding.lineChart.invalidate()
    }




    private class XAxisFormatter(private val labels: Array<String>) : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index >= 0 && index < labels.size) labels[index] else ""
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationService.startLocationUpdates()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationService.stopLocationUpdates()
    }



    private fun checkLocationPermission() {
        if (PermissionUtils.isLocationPermissionGranted(requireContext())) {
            locationService.startLocationUpdates()
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

}