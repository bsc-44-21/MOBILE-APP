package com.example.labwork.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

data class Task(
    var title: String,
    var description: String,
    var date: String,
    var time: String
)

@Composable
fun TaskReminderScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var editingIndex by remember { mutableStateOf(-1) }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "${dayOfMonth}/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            selectedTime = String.format("%02d:%02d", hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFF5F5F5)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (editingIndex == -1) "Add Task" else "Edit Task",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (selectedDate.isEmpty()) "Select Date" else selectedDate)
                }

                Button(
                    onClick = { timePickerDialog.show() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (selectedTime.isEmpty()) "Select Time" else selectedTime)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (taskTitle.isNotBlank() && taskDescription.isNotBlank() && selectedDate.isNotBlank() && selectedTime.isNotBlank()) {
                        val newTask = Task(taskTitle, taskDescription, selectedDate, selectedTime)
                        if (editingIndex == -1) {
                            tasks = tasks + newTask
                            Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show()
                        } else {
                            tasks = tasks.toMutableList().also { it[editingIndex] = newTask }
                            editingIndex = -1
                            Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show()
                        }

                        taskTitle = ""
                        taskDescription = ""
                        selectedDate = ""
                        selectedTime = ""
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (editingIndex == -1) "Add Task" else "Update Task",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                itemsIndexed(tasks) { index, task ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = task.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = task.description,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "${task.date} ${task.time}",
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }

                            Row {
                                IconButton(onClick = {
                                    // Edit task
                                    taskTitle = task.title
                                    taskDescription = task.description
                                    selectedDate = task.date
                                    selectedTime = task.time
                                    editingIndex = index
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Edit Task",
                                        tint = Color(0xFF2E7D32)
                                    )
                                }

                                IconButton(onClick = {
                                    tasks = tasks.toMutableList().also { it.removeAt(index) }
                                    Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
                                    if (editingIndex == index) {
                                        taskTitle = ""
                                        taskDescription = ""
                                        selectedDate = ""
                                        selectedTime = ""
                                        editingIndex = -1
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete Task",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
