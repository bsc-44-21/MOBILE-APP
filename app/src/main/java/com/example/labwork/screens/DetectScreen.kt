package com.example.labwork.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetectScreen(modifier: Modifier = Modifier) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var recentScans by remember { mutableStateOf(listOf<Uri>()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            recentScans = listOf(it) + recentScans
        }
    }

    Surface(
        color = Color(0xFFF5F5F5),
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))

            // 🖼️ Banner container
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        "https://img.freepik.com/premium-photo/farmer-using-tablet-lush-green-field-showcasing-modern-agricultural-technology-sustainable-farming-practices_1317057-27747.jpg"
                    ),
                    contentDescription = "Farmer Detecting Disease",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 📷 Styled Pick Image button
            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Detect Your Crops Diseases",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📸 Selected Image
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 🧠 Recent Scans Section in Card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recent Scans",
                        color = Color(0xFF2E7D32),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val diseaseDescriptions = listOf(
                        "Maize Leaf Blight 🌾 — symptoms include yellowing and lesions on the leaves. Recommended treatment: apply a copper-based fungicide and remove infected leaves.",
                        "Tomato Early Blight 🍅 — brown spots on older leaves, causing leaf drop. Treat with neem oil spray and crop rotation.",
                        "Potato Late Blight 🥔 — dark lesions on leaves and tubers. Use fungicide and avoid wetting leaves when irrigating."
                    )

                    diseaseDescriptions.forEach { description ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = buildAnnotatedString {
                                val split = description.split("—")
                                if (split.size == 2) {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(split[0].trim())
                                    }
                                    append(" —${split[1]}")
                                } else {
                                    append(description)
                                }
                            },
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                    ) {
                        items(recentScans) { scan ->
                            Image(
                                painter = rememberAsyncImagePainter(scan),
                                contentDescription = "Recent Scan",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .padding(vertical = 6.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}
