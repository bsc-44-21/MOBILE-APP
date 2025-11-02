package com.example.labwork.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DummyWeather(val temperature: String, val humidity: String, val condition: String)
data class WeeklyInsight(val day: String, val temp: String, val condition: String)

@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    // Dummy weather data
    var weatherList by remember {
        mutableStateOf(
            listOf(
                DummyWeather("25", "60", "Sunny"),
                DummyWeather("22", "70", "Rainy"),
                DummyWeather("28", "55", "Cloudy")
            )
        )
    }

    // Dummy weekly insights
    val weeklyInsights = listOf(
        WeeklyInsight("Mon", "25°C", "Sunny"),
        WeeklyInsight("Tue", "23°C", "Cloudy"),
        WeeklyInsight("Wed", "22°C", "Rainy"),
        WeeklyInsight("Thu", "26°C", "Sunny"),
        WeeklyInsight("Fri", "28°C", "Sunny"),
        WeeklyInsight("Sat", "24°C", "Cloudy"),
        WeeklyInsight("Sun", "27°C", "Sunny")
    )

    Surface(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF5F5F5), Color(0xFFF5F5F5))
                    )
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Local Weather Info",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(weatherList) { weather ->
                        val conditionColor = when (weather.condition.lowercase()) {
                            "sunny" -> Color(0xFFFFD54F)
                            "rainy" -> Color(0xFF4FC3F7)
                            "cloudy" -> Color(0xFFB0BEC5)
                            else -> Color.LightGray
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = when (weather.condition.lowercase()) {
                                        "sunny" -> "☀️"
                                        "rainy" -> "🌧️"
                                        "cloudy" -> "☁️"
                                        else -> "❓"
                                    },
                                    fontSize = 36.sp
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Column {
                                    Text(
                                        text = "${weather.temperature}°C",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Humidity: ${weather.humidity}%",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = weather.condition,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = conditionColor
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Weekly Insights - Horizontal Scroll
                Text(
                    text = "Weekly Insights",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(weeklyInsights) { insight ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(100.dp)
                                .height(120.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = insight.day,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = insight.temp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = when (insight.condition.lowercase()) {
                                        "sunny" -> "☀️"
                                        "rainy" -> "🌧️"
                                        "cloudy" -> "☁️"
                                        else -> "❓"
                                    },
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { weatherList = weatherList.shuffled() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text(text = "Refresh Weather", color = Color.White)
                }
            }
        }
    }
}
