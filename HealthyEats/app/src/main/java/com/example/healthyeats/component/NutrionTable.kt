package com.example.healthyeats.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data class to hold nutrition information
data class NutritionItem(
    val nutrition: String,
    val ideal: Float,
    val real: Float
)

// Composable function for a single row in the table
@Composable
fun NutritionTableRow(item: NutritionItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.nutrition)
        Text(text = "%.2f".format(item.ideal))
        Text(text = "%.2f".format(item.real))
        val change = item.real - item.ideal
        Text(text = "%.2f".format(change))
    }
}

// Main composable function to display the table
@Composable
fun NutritionTable(nutritionList: List<NutritionItem>) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Nutrition")
            Text("Ideal (g)")
            Text("Real (g)")
            Text("Change (g)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Iterate over the list and create a row for each item
        nutritionList.forEach { item ->
            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            NutritionTableRow(item = item)
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
    }
}

// Preview with sample data
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val sampleData = listOf(
        NutritionItem("Protein", 50.0f, 45.0f),
        NutritionItem("Carbo", 100.0f, 95.0f),
        NutritionItem("Fats", 70.0f, 75.0f),
        NutritionItem("Fiber", 25.0f, 20.0f)
    )
    NutritionTable(nutritionList = sampleData)
}