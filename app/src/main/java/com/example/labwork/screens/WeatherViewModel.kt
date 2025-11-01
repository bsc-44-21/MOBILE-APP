package com.example.labwork.screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.labwork.data.WeatherApiService
import com.example.labwork.data.WeatherResponse
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

sealed interface WeatherUiState {
    data class Success(val data: WeatherResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
    object Loading : WeatherUiState
}

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherUiState = mutableStateOf<WeatherUiState>(WeatherUiState.Loading)
    val weatherUiState: State<WeatherUiState> = _weatherUiState

    private val weatherApiService: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApiService = retrofit.create(WeatherApiService::class.java)
    }

    @SuppressLint("MissingPermission")
    fun getWeather() {
        _weatherUiState.value = WeatherUiState.Loading
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplication<Application>())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModelScope.launch {
                    try {
                        val response = weatherApiService.getWeather(
                            lat = location.latitude,
                            lon = location.longitude,
                            appid = "c2e89da71238a17ebf60af25f86f517a" // TODO: Replace with your OpenWeatherMap API key
                        )
                        _weatherUiState.value = WeatherUiState.Success(response)
                    } catch (e: Exception) {
                        _weatherUiState.value = WeatherUiState.Error("Failed to fetch weather data: ${e.message}")
                    }
                }
            } else {
                _weatherUiState.value = WeatherUiState.Error("Could not retrieve location. Make sure location is enabled.")
            }
        }.addOnFailureListener { e ->
             _weatherUiState.value = WeatherUiState.Error("Failed to get location: ${e.message}")
        }
    }
}
