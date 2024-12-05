package com.example.healthyeats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TopAppBar(
    currentDate: String,
    onDateChange: (String) -> Unit,
    onDateClick: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DateSelectorDialog(
            currentDate = currentDate,
            onDateSelected = { newDate ->
                onDateChange(newDate)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Previous button
        IconButton(onClick = {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = sdf.parse(currentDate)!!
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            onDateChange(sdf.format(calendar.time))
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Previous Day"
            )
        }

        // Date display
        Box(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp) // Match the minimum height of IconButton
                .clickable { showDatePicker = true }
        ) {
            Text(
                text = currentDate,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center, // Center the text horizontally
                modifier = Modifier
                    .align(Alignment.Center) // Center the text within the Box
            )
        }

        // Next button
        IconButton(onClick = {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = sdf.parse(currentDate)!!
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            onDateChange(sdf.format(calendar.time))
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Next Day"
            )
        }
    }
}

// Preview function for TopAppBar with default parameters
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    // Providing a sample date string and dummy functions for preview purposes
    val sampleDate = "2024-12-05"
    MaterialTheme {
        TopAppBar(
            currentDate = sampleDate,
            onDateChange = {},
            onDateClick = {}
        )
    }
}