package com.astraleratech.priviobrowser.features.wheather

import com.astraleratech.priviobrowser.utils.Keys
import com.astraleratech.priviobrowser.utils.RetrofitInstance

class WeatherRepository {

    // Function to fetch weather data
    suspend fun getWeatherData(lat: Double, lng: Double): WeatherResponseDM {
        val latitude = lat
        val longitude = lng
        val apiKey = Keys.wheatherAPiKEy
        return RetrofitInstance.api.getWeatherData(latitude, longitude, apiKey)
    }
}
