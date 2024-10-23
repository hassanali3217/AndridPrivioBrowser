package com.astraleratech.priviobrowser.features.wheather

import com.astraleratech.priviobrowser.utils.RetrofitInstance

class WeatherRepository {

    // Function to fetch weather data
    suspend fun getWeatherData(lat:Double , lng :Double): WeatherResponseDM {
        val latitude = lat
        val longitude = lng
        val apiKey = "0d6c4b462264bfa47d3a9f4276d38fd5"
        return RetrofitInstance.api.getWeatherData(latitude, longitude, apiKey)
    }
}
