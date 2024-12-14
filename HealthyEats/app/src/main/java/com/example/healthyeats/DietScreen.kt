package com.example.healthyeats

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
//        topBar = {
//            TopAppBar(
//                currentDate = currentDate,
//                onDateChange = { newDate ->
//                    currentDate = newDate
//                },
//                onDateClick = {
//
//                }
//            )
//        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val mealTypes = listOf(
                Triple(R.drawable.ic_breakfast, R.string.breakfast, breakfastItems),
                Triple(R.drawable.ic_lunch, R.string.lunch, lunchItems),
                Triple(R.drawable.ic_dinner, R.string.dinner, dinnerItems)
            )
            items(mealTypes) { (icon, titleRes, items) ->
                DietCardItem(
                    icon = icon,
                    title = stringResource(titleRes),
                    items = items,
                    onAddClick = {
                        when (titleRes) {
                            R.string.breakfast -> selectedMealType = MainViewModel.TYPE_BREAKFAST
                            R.string.lunch -> selectedMealType = MainViewModel.TYPE_LUNCH
                            R.string.dinner -> selectedMealType = MainViewModel.TYPE_DINNER
                        }
                        showAddDietDialog = true
                    },
                    onDeleteClick = { toDelete ->
                        viewModel.removeDietList(toDelete)
                    }
                )
                // 在每个卡片之间添加一些间距
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        //add diet dialog
        if (showAddDietDialog) {
            AlertDialog(
                onDismissRequest = { showAddDietDialog = false },
                title = { Text("Add to $selectedMealType") },
                text = {
                    AddDietForm(
                        onDietAdded = { newDiet, _ ->
                            viewModel.addDiet(newDiet.copy(type = selectedMealType))
                            showAddDietDialog = false
                        },
                        onCanceled = {
                            showAddDietDialog = false
                        },
                        viewModel = viewModel,
                        autocomplete = true
                    )
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