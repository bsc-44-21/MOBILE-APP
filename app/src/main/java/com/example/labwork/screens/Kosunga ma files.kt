//THIS IS MAINACTIVITY'S CODE
//package com.example.labwork
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.SmartToy
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Cloud
//import androidx.compose.material.icons.filled.Translate
//import androidx.compose.material.icons.filled.Description
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.safeContentPadding
//import com.example.labwork.screens.AgriBotScreen
//import com.example.labwork.screens.DetectScreen
//import com.example.labwork.screens.TaskReminderScreen
//import com.example.labwork.screens.WeatherScreen
//import com.example.labwork.ui.theme.LabworkTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LabworkTheme {
//                CropiaApp()
//            }
//        }
//    }
//}
//
//// Navigation screens
//sealed class Screen(val title: String, val icon: ImageVector) {
//    object Detect : Screen("Home", Icons.Filled.Home)              // Detect/Scan → Home
//    object AgriBot : Screen("AgriBot", Icons.Filled.SmartToy)      // AgriBot → AI assistant
//    object Weather : Screen("Weather", Icons.Filled.Cloud)         // Weather
//    object Reminder : Screen("Tasks", Icons.Filled.Description)   // Reminder → Document/Notepad icon
//}
//
//@Composable
//fun CropiaApp() {
//    val screens = listOf(
//        Screen.Detect,
//        Screen.AgriBot,
//        Screen.Weather,
//        Screen.Reminder
//    )
//
//    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Detect) }
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        containerColor = Color.White,
//        bottomBar = {
//            NavigationBar(containerColor = Color(0xFF2E7D32)) {
//                screens.forEach { screen ->
//                    val selected = screen == selectedScreen
//                    NavigationBarItem(
//                        selected = selected,
//                        onClick = { selectedScreen = screen },
//                        icon = {
//                            Icon(
//                                screen.icon,
//                                contentDescription = screen.title,
//                                tint = if (selected) Color.Green else Color(0xFFFFFFFF)
//                            )
//                        },
//                        label = {
//                            Text(
//                                screen.title,
//                                color = if (selected) Color.White else Color(0xFFC8E6C9)
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
//                .safeContentPadding()
//        ) {
//            // Top section with app name, notifications, and translator
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "MbeuGuard",
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF2E7D32)
//                )
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    IconButton(onClick = { /* handle notifications */ }) {
//                        Icon(
//                            Icons.Filled.Notifications,
//                            contentDescription = "Notifications",
//                            tint = Color(0xFF2E7D32)
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(12.dp))
//                    IconButton(onClick = { /* handle language translator */ }) {
//                        Icon(
//                            Icons.Filled.Translate,
//                            contentDescription = "Translator",
//                            tint = Color(0xFF2E7D32)
//                        )
//                    }
//                }
//            }
//
//            // Current screen content
//            when (selectedScreen) {
//                Screen.Detect -> DetectScreen(Modifier.padding(8.dp))
//                Screen.AgriBot -> AgriBotScreen(Modifier.padding(8.dp))
//                Screen.Weather -> WeatherScreen(Modifier.padding(8.dp))
//                Screen.Reminder -> TaskReminderScreen(Modifier.padding(8.dp))
//            }
//        }
//    }
//}
//
//@Composable
//fun CropiaSurface(content: @Composable () -> Unit) {
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color.White
//    ) {
//        content()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCropiaApp() {
//    LabworkTheme {
//        CropiaApp()
//    }
//}
//


/////////THIS IS DETECTSCREEN'S CODE/////
//package com.example.labwork.screens
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberAsyncImagePainter
//
//@Composable
//fun DetectScreen(modifier: Modifier = Modifier) {
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
//    var recentScans by remember { mutableStateOf(listOf<Uri>()) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            selectedImageUri = it
//            recentScans = listOf(it) + recentScans
//        }
//    }
//
//    Surface(
//        color = Color(0xFFF5F5F5),
//        modifier = modifier.fillMaxSize()
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 🖼️ Banner container
//            Card(
//                shape = RoundedCornerShape(16.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 12.dp)
//            ) {
//                Image(
//                    painter = rememberAsyncImagePainter(
//                        "https://img.freepik.com/premium-photo/farmer-using-tablet-lush-green-field-showcasing-modern-agricultural-technology-sustainable-farming-practices_1317057-27747.jpg"
//                    ),
//                    contentDescription = "Farmer Detecting Disease",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(220.dp)
//                        .clip(RoundedCornerShape(16.dp)),
//                    contentScale = ContentScale.Crop
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // 📷 Styled Pick Image button
//            Button(
//                onClick = { launcher.launch("image/*") },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
//                shape = RoundedCornerShape(16.dp),
//                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//            ) {
//                Text(
//                    text = "Detect Your Crops Diseases",
//                    color = Color.White,
//                    fontSize = 18.sp
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 📸 Selected Image
//            selectedImageUri?.let { uri ->
//                Image(
//                    painter = rememberAsyncImagePainter(uri),
//                    contentDescription = "Selected Image",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(220.dp)
//                        .clip(RoundedCornerShape(16.dp)),
//                    contentScale = ContentScale.Crop
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//            // 🧠 Recent Scans Section in Card
//            Card(
//                shape = RoundedCornerShape(16.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Recent Scans",
//                        color = Color(0xFF2E7D32),
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    val diseaseDescriptions = listOf(
//                        "Maize Leaf Blight 🌾 — symptoms include yellowing and lesions on the leaves. Recommended treatment: apply a copper-based fungicide and remove infected leaves.",
//                        "Tomato Early Blight 🍅 — brown spots on older leaves, causing leaf drop. Treat with neem oil spray and crop rotation.",
//                        "Potato Late Blight 🥔 — dark lesions on leaves and tubers. Use fungicide and avoid wetting leaves when irrigating."
//                    )
//
//                    diseaseDescriptions.forEach { description ->
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = buildAnnotatedString {
//                                val split = description.split("—")
//                                if (split.size == 2) {
//                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                                        append(split[0].trim())
//                                    }
//                                    append(" —${split[1]}")
//                                } else {
//                                    append(description)
//                                }
//                            },
//                            color = Color.Gray,
//                            fontSize = 14.sp
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(max = 300.dp)
//                    ) {
//                        items(recentScans) { scan ->
//                            Image(
//                                painter = rememberAsyncImagePainter(scan),
//                                contentDescription = "Recent Scan",
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(180.dp)
//                                    .padding(vertical = 6.dp)
//                                    .clip(RoundedCornerShape(12.dp)),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
