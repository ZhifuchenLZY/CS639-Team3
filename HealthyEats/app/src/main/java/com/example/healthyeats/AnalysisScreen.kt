package com.example.healthyeats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.component.NutritionPieChart
import com.example.healthyeats.component.NutritionTable
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun AnalysisScreen(viewModel: MainViewModel) {
    val nutritionMap = viewModel.nutritionStats.collectAsState().value
    val protein = nutritionMap[MainViewModel.KEY_PROTEIN]!!
    val fat = nutritionMap[MainViewModel.KEY_FAT]!!
    val carbo = nutritionMap[MainViewModel.KEY_CARBO]!!
    val calories = nutritionMap[MainViewModel.KEY_CALORIES]!!
    val total = protein + fat + carbo + 0.0001
    val pieChartData = listOf(
        Pair("Protein", (protein / total).toFloat() * 100f),
        Pair("Fats", (fat / total).toFloat() * 100f),
        Pair("Carbs", (carbo / total).toFloat() * 100f)
    )
    //title
    val sampleData = listOf(
        NutritionItem(stringResource(R.string.protein), protein, stringResource(R.string.g)),
        NutritionItem(stringResource(R.string.fat), fat, stringResource(R.string.g)),
        NutritionItem(stringResource(R.string.carbohydrate), carbo, stringResource(R.string.g)),
        NutritionItem(stringResource(R.string.calories), calories, stringResource(R.string.kcal))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //pie chart
        Row(modifier = Modifier.weight(1f)) {
            NutritionPieChart(pieChartData)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            NutritionTable(nutritionList = sampleData)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnalysisScreen() {
    val dietViewModel = MainViewModel()
    HealthyEatsTheme {
        AnalysisScreen(dietViewModel)
    }
}
