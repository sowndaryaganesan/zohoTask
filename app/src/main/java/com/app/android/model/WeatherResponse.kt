package com.app.android.model


data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val dt: Long,
    val sys: Sys,
    val clouds: Clouds,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int,
    val list: List<Lists>,
    val dt_txt: String
) {

    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Weather(

        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Main(
        val temp: Double,
        val feelsLike: Double,
        val tempMin: Double,
        val tempMax: Double,
        val pressure: Int,
        val humidity: Int
    )

    data class Wind(
        val speed: Double,
        val deg: Int
    )

    data class Clouds(
        val all: Int
    )

    data class Sys(
        val type: Int,
        val id: Int,
        val country: String,
        val sunrise: Long,
        val sunset: Long
    )
    data class Lists(
        val main: Mains,
        val components: Components,
        val dt: Long
    )

    data class Mains(
        val aqi: Int
    )

    data class Components(
        val co: Double,
        val no: Double,
        val no2: Double,
        val o3: Double,
        val so2: Double,
        val pm2_5: Double,
        val pm10: Double,
        val nh3: Double
    )
}