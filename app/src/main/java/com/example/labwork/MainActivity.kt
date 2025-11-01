package com.example.labwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labwork.screens.AgriBotScreen
import com.example.labwork.screens.DetectScreen
import com.example.labwork.screens.TaskReminderScreen
import com.example.labwork.screens.WeatherScreen
import com.example.labwork.ui.theme.LabworkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabworkTheme {
                CropiaApp()
            }
        }
    }
}

// Navigation screens
sealed class Screen(val title: String, val icon: ImageVector) {
    object Detect : Screen("Detect", Icons.Filled.Search)
    object AgriBot : Screen("AgriBot", Icons.Filled.Person)
    object Weather : Screen("Weather", Icons.Filled.Search)
    object Reminder : Screen("Reminder", Icons.Filled.Notifications)
}

@Composable
fun CropiaApp() {
    val screens = listOf(
        Screen.Detect,
        Screen.AgriBot,
        Screen.Weather,
        Screen.Reminder
    )

    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Detect) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF2E7D32)) {
                screens.forEach { screen ->
                    val selected = screen == selectedScreen
                    NavigationBarItem(
                        selected = selected,
                        onClick = { selectedScreen = screen },
                        icon = {
                            Icon(
                                screen.icon,
                                contentDescription = screen.title,
                                tint = if (selected) Color.Green else Color(0xFFFFFFFF)
                            )
                        },
                        label = {
                            Text(
                                screen.title,
                                color = if (selected) Color.White else Color(0xFFC8E6C9)
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()) // ✅ Leaves space for time/battery
        ) {
            // Top section with app name and notification icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "MbeuGuard",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
                IconButton(onClick = { /* handle notifications */ }) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "Notifications",
                        tint = Color(0xFF2E7D32)
                    )
                }
            }

            // Current screen content
            when (selectedScreen) {
                Screen.Detect -> DetectScreen(Modifier.padding(8.dp))
                Screen.AgriBot -> AgriBotScreen(Modifier.padding(8.dp))
                Screen.Weather -> WeatherScreen(Modifier.padding(8.dp))
                Screen.Reminder -> TaskReminderScreen(Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun CropiaSurface(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCropiaApp() {
    LabworkTheme {
        CropiaApp()
    }
}
