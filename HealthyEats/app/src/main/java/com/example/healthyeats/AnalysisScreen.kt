package com.example.healthyeats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.component.NutritionPieChart
import com.example.healthyeats.component.NutritionTable
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun AnalysisScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //pie chart
        val dietData = listOf(
            Pair("Protein", 30f),
            Pair("Carbs", 40f),
            Pair("Fats", 30f)
        )
        Row(modifier = Modifier.weight(1f)) {
            NutritionPieChart(dietData)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            //title
            val sampleData = listOf(
                NutritionItem("Protein", 50.0f, 45.0f),
                NutritionItem("Carbo", 100.0f, 95.0f),
                NutritionItem("Fats", 70.0f, 75.0f)
            )
            NutritionTable(nutritionList = sampleData)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnalysisScreen() {
    HealthyEatsTheme {
        AnalysisScreen()
    }
}
