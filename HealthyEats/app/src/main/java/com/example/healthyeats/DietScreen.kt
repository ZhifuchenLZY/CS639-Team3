package com.example.healthyeats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.component.AddDietForm
import com.example.healthyeats.component.DietCardItem
import com.example.healthyeats.component.TopAppBar
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun DietScreen(viewModel: MainViewModel) {
    var currentDate by remember { mutableStateOf("2024-12-06") }
    var showAddDietDialog by remember { mutableStateOf(false) }
    var selectedMealType by remember { mutableStateOf("") }

    val breakfastItems by viewModel.breakfastItems.collectAsState()
    val lunchItems by viewModel.lunchItems.collectAsState()
    val dinnerItems by viewModel.dinnerItems.collectAsState()

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
            DietCardItem(
                icon = R.drawable.ic_breakfast,
                title = stringResource(R.string.breakfast),
                items = breakfastItems,
                onAddClick = {
                    selectedMealType = MainViewModel.TYPE_BREAKFAST
                    showAddDietDialog = true
                },
                onDeleteClick = { toDelete ->
                    run {
                        viewModel.removeDietList(toDelete)
                    }
                }
            )

            DietCardItem(
                icon = R.drawable.ic_lunch,
                title = stringResource(R.string.lunch),
                items = lunchItems,
                onAddClick = {
                    selectedMealType = MainViewModel.TYPE_LUNCH
                    showAddDietDialog = true
                },
                onDeleteClick = { toDelete ->
                    run {
                        viewModel.removeDietList(toDelete)
                    }
                }
            )

            DietCardItem(
                icon = R.drawable.ic_dinner,
                title = stringResource(R.string.dinner),
                items = dinnerItems,
                onAddClick = {
                    selectedMealType = MainViewModel.TYPE_DINNER
                    showAddDietDialog = true
                },
                onDeleteClick = { toDelete ->
                    run {
                        viewModel.removeDietList(toDelete)
                    }
                }
            )
        }
        //add diet dialog
        if (showAddDietDialog) {
            AlertDialog(
                onDismissRequest = { showAddDietDialog = false },
                title = { Text("Add to $selectedMealType") },
                text = {
                    AddDietForm(onDietAdded = { newDiet ->
                        newDiet.type = selectedMealType
                        viewModel.addDiet(newDiet.copy(type = selectedMealType))
                        showAddDietDialog = false
                    }, onCanceled = {
                        showAddDietDialog = false
                    })
                },
                confirmButton = {
                    //not use
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDietScreen() {
    val dietViewModel = MainViewModel()
    HealthyEatsTheme {
        DietScreen(dietViewModel)
    }
}