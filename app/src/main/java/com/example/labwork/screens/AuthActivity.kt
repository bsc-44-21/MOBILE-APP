//package com.example.labwork.screens
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.platform.LocalContext
//import com.example.labwork.MainActivity
//
//class AuthActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            AuthScreen()
//        }
//    }
//}
//
//@Composable
//fun AuthScreen() {
//    val context = LocalContext.current
//
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color(0xFFF5F5F5)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(32.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Login / Sign Up",
//                fontSize = 28.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF2E7D32)
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            OutlinedTextField(
//                value = username,
//                onValueChange = { username = it },
//                label = { Text("Username") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    // For demo, just navigate to MainActivity
//                    context.startActivity(Intent(context, MainActivity::class.java))
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//            ) {
//                Text(
//                    text = "Login / Sign Up",
//                    color = Color.White,
//                    fontSize = 16.sp
//                )
//            }
//        }
//    }
//}
