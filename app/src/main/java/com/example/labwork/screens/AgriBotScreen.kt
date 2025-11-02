package com.example.labwork.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun AgriBotScreen(modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<ChatMessage>()) }

    Surface(
        color = Color(0xFFF5F5F5), // soft background
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.SmartToy, // ✅ SmartToy icon
                        contentDescription = "AgriBot Icon",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "AgriBot Assistant",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Chat area
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                reverseLayout = true
            ) {
                items(chatHistory.reversed()) { message ->
                    ChatBubble(message)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input bar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        placeholder = { Text("Ask about crops, soil, or farming tips...") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color(0xFF2E7D32)
                        )
                    )

                    IconButton(
                        onClick = {
                            if (userInput.isNotBlank()) {
                                chatHistory = chatHistory + ChatMessage(userInput, true)
                                val botResponse = getBotResponse(userInput)
                                chatHistory = chatHistory + ChatMessage(botResponse, false)
                                userInput = ""
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF2E7D32))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send Message",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isUser) Color(0xFFD0F8CE) else Color(0xFFFFFFFF)
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = message.text,
                color = Color.Black,
                fontSize = 15.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

// Simple AI bot logic
fun getBotResponse(query: String): String {
    val lower = query.lowercase()
    return when {
        "hello" in lower || "hi" in lower -> "Hi there! 🌿 How can I help with your crops today?"
        "help" in lower -> "You can ask about soil types, fertilizer use, pest control, or planting seasons."
        "soil" in lower -> "Healthy soil is rich in organic matter and well-drained. Test pH regularly!"
        "maize" in lower -> "Maize grows best in warm, sunny climates with moderate rainfall and well-drained soil."
        "fertilizer" in lower -> "Use organic compost or NPK fertilizer sparingly for better soil balance."
        "pests" in lower -> "To control pests naturally, try neem oil or intercropping with pest-repelling plants."
        "rain" in lower -> "Rain is essential — ensure good drainage to prevent waterlogging."
        else -> "I'm AgriBot 🤖🌾 — your digital farm assistant. Ask me anything about crops or agriculture!"
    }
}
