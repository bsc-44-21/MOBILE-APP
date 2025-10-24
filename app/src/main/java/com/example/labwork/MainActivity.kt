package com.example.labwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.labwork.ui.theme.LabworkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabworkTheme {
                CropiaApp()
            }
        }
    }
}

@Composable
fun CropiaApp() {
    val screens = listOf(
        Screen.Home,
        Screen.Weather,
        Screen.Tasks,
        Screen.Profile
    )

    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White, // 🌿 White background
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF2E7D32)) { // Dark green bar
                screens.forEach { screen ->
                    val selected = screen == selectedScreen
                    NavigationBarItem(
                        selected = selected,
                        onClick = { selectedScreen = screen },
                        icon = {
                            Icon(
                                screen.icon,
                                contentDescription = screen.title,
                                tint = if (selected) Color.White else Color(0xFFC8E6C9) // lighter green tint
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                color = if (selected) Color.White else Color(0xFFC8E6C9)
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedScreen) {
            Screen.Home -> HomeScreen(Modifier.padding(innerPadding))
            Screen.Weather -> WeatherScreen(Modifier.padding(innerPadding))
            Screen.Tasks -> TaskReminderScreen(Modifier.padding(innerPadding))
            Screen.Profile -> ProfileScreen(Modifier.padding(innerPadding))
        }
    }
}

sealed class Screen(val title: String, val icon: ImageVector) {
    object Home : Screen("Home", Icons.Filled.Home)
    object Weather : Screen("Weather", Icons.Filled.Search)
    object Tasks : Screen("Tasks", Icons.Filled.List)
    object Profile : Screen("Profile", Icons.Filled.Person)
}

// 🌱 Common composable style
@Composable
fun CropiaSurface(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        content()
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    CropiaSurface {
        Text(
            text = "Detect Crop Disease",
            color = Color(0xFF2E7D32),
            modifier = modifier.padding(16.dp)
        )
    }
}

@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    CropiaSurface {
        Text(
            text = "Local Weather Info",
            color = Color(0xFF2E7D32),
            modifier = modifier.padding(16.dp)
        )
    }
}

@Composable
fun TaskReminderScreen(modifier: Modifier = Modifier) {
    CropiaSurface {
        Text(
            text = "Set and View Tasks",
            color = Color(0xFF2E7D32),
            modifier = modifier.padding(16.dp)
        )
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    CropiaSurface {
        Text(
            text = "User Info",
            color = Color(0xFF2E7D32),
            modifier = modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCropiaApp() {
    LabworkTheme {
        CropiaApp()
    }
}
