package com.astraleratech.priviobrowser.features.wheather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponseDM>()
    val weatherData: LiveData<WeatherResponseDM> = _weatherData

    fun fetchWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeatherData(latitude, longitude)
                _weatherData.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
