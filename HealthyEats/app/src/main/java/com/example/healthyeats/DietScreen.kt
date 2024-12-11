package com.example.healthyeats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.component.DietCardItem
import com.example.healthyeats.component.TopAppBar
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun DietScreen(viewModel: DietViewModel) {
    var currentDate by remember { mutableStateOf("2024-12-06") }
    var showAddDietDialog by remember { mutableStateOf(false) }
    var selectedMealType by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                currentDate = currentDate,
                onDateChange = { newDate ->
                    currentDate = newDate
                },
                onDateClick = {

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
            viewModel.dietList.value?.filter { it.type == "Breakfast" }?.let { it ->
                DietCardItem(
                    icon = R.drawable.ic_breakfast,
                    title = stringResource(R.string.breakfast),
                    items = it.map { it.name },
                    onAddClick = {
                        selectedMealType = "Breakfast"
                        showAddDietDialog = true
                    },
                    onDeleteClick = {

                    }
                )
            }

            viewModel.dietList.value?.filter { it.type == "Lunch" }?.let { it ->
                DietCardItem(
                    icon = R.drawable.ic_lunch,
                    title = stringResource(R.string.lunch),
                    items = it.map { it.name },
                    onAddClick = {
                        selectedMealType = "Lunch"
                        showAddDietDialog = true
                    },
                    onDeleteClick = {

                    }
                )
            }

            viewModel.dietList.value?.filter { it.type == "Dinner" }?.let { it ->
                DietCardItem(
                    icon = R.drawable.ic_dinner,
                    title = stringResource(R.string.dinner),
                    items = it.map { it.name },
                    onAddClick = {
                        selectedMealType = "Dinner"
                        showAddDietDialog = true
                    },
                    onDeleteClick = {

                    }
                )
            }
        }

        if (showAddDietDialog) {
            AlertDialog(
                onDismissRequest = { showAddDietDialog = false },
                title = { Text("Add to $selectedMealType") },
                text = {
                    AddDietForm(onDietAdded = { newDiet ->
                        viewModel.addDiet(newDiet.copy(type = selectedMealType))
                        showAddDietDialog = false
                    }, onCanceled = {
                        showAddDietDialog = false
                    })
                },
                confirmButton = {

                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDietScreen() {
    val dietViewModel = DietViewModel()
    HealthyEatsTheme {
        DietScreen(dietViewModel)
    }
}