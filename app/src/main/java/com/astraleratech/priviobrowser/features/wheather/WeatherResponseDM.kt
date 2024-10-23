package com.astraleratech.priviobrowser.features.wheather

data class WeatherResponseDM( val coord: Coord,
                              val weather: List<Weather>,
                              val base: String,
                              val main: Main,
                              val visibility: Int,
                              val wind: Wind,
                              val clouds: Clouds,
                              val dt: Long,
                              val sys: Sys,
                              val timezone: Int,
                              val id: Int,
                              val name: String,
                              val cod: Int)
data class Coord(
    val lon: Double,
    val lat: Double
)

// Weather data model
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

// Main weather data model
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)

// Wind data model
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

// Clouds data model
data class Clouds(
    val all: Int
)

// System information data model
data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)