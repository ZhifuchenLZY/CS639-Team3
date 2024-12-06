package com.example.healthyeats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.component.DietCardItem
import com.example.healthyeats.component.TopAppBar
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun DietScreen() {
    var currentDate by remember { mutableStateOf("2024-12-06") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                currentDate = currentDate,
                onDateChange = { newDate ->
                    currentDate = newDate
                },
                onDateClick = {
//                    coroutineScope.launch {
//                        showDatePickerDialog(activity, currentDate) { selectedDate ->
//                            currentDate = selectedDate
//                        }
//                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DietCardItem(
                icon = R.drawable.ic_breakfast,
                title = stringResource(R.string.breakfast),
                items = listOf("Breakfast", "Lunch", "Dinner"),
                onAddClick = {

                },
                onDeleteClick = {

                }
            )
            DietCardItem(
                icon = R.drawable.ic_lunch,
                title = stringResource(R.string.lunch),
                items = listOf("Breakfast", "Lunch", "Dinner"),
                onAddClick = {

                },
                onDeleteClick = {

                }
            )
            DietCardItem(
                icon = R.drawable.ic_dinner,
                title = stringResource(R.string.dinner),
                items = listOf("Breakfast", "Lunch", "Dinner"),
                onAddClick = {

                },
                onDeleteClick = {

                }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewDietScreen() {
    HealthyEatsTheme {
        DietScreen()
    }
}