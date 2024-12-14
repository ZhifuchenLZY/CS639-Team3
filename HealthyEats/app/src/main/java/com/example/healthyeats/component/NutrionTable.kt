package com.example.healthyeats.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.NutritionItem
import com.example.healthyeats.R


// Composable function for a single row in the table
@Composable
fun NutritionTableRow(item: NutritionItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        val textModifier = Modifier
            .weight(1f)
            .widthIn(max = Int.MAX_VALUE.dp)
            .padding(4.dp)
        Text(text = item.name, modifier = textModifier)
        Text(text = "%.2f".format(item.amount), modifier = textModifier)
    }
}

// Main composable function to display the table
@Composable
fun NutritionTable(nutritionList: List<NutritionItem>) {
    Column(modifier = Modifier.padding(16.dp)) {
        val textModifier = Modifier
            .weight(1f)
            .align(Alignment.CenterHorizontally)
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(stringResource(R.string.nutrition), modifier = textModifier)
            Text(stringResource(R.string.amount_g), modifier = textModifier)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Iterate over the list and create a row for each item
        LazyColumn(modifier = Modifier.padding(top = 4.dp)) {
            items(nutritionList) { item ->
                NutritionTableRow(item)
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            }

        }
    }
}

// Preview with sample data
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val sampleData = listOf(
        NutritionItem("Protein",  45.0,"g"),
        NutritionItem("Carbo", 95.0,"g"),
        NutritionItem("Fats", 75.0,"g")
    )
    NutritionTable(nutritionList = sampleData)
}