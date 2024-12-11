package com.example.healthyeats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddDietForm(onDietAdded: (Diet) -> Unit) {
    var name by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var calorie by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var carbohydrate by remember { mutableStateOf("") }
    var others by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (g)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = calorie,
            onValueChange = { calorie = it },
            label = { Text("Calorie") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = protein,
            onValueChange = { protein = it },
            label = { Text("Protein (g)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = fat,
            onValueChange = { fat = it },
            label = { Text("Fat (g)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = carbohydrate,
            onValueChange = { carbohydrate = it },
            label = { Text("Carbohydrate (g)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = others,
            onValueChange = { others = it },
            label = { Text("Others") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newDiet = Diet(
                    id = 0,
                    name = name,
                    weight = weight.toDoubleOrNull() ?: 0.0,
                    calorie = calorie.toDoubleOrNull() ?: 0.0,
                    protein = protein.toDoubleOrNull() ?: 0.0,
                    fat = fat.toDoubleOrNull() ?: 0.0,
                    carbohydrate = carbohydrate.toDoubleOrNull() ?: 0.0,
                    others = others
                )
                onDietAdded(newDiet)
            },
            enabled = name.isNotEmpty() && weight.isNotEmpty()
                    && calorie.isNotEmpty() && protein.isNotEmpty()
                    && fat.isNotEmpty() && carbohydrate.isNotEmpty()
        ) {
            Text("Add Diet")
        }
    }
}

@Preview
@Composable
fun CardItemPreview() {
    AddDietForm() {

    }
}