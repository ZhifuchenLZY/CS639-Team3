package com.example.healthyeats.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.R
import com.example.healthyeats.Diet
import java.time.LocalDate

@Composable
fun AddDietForm(onDietAdded: (Diet) -> Unit, onCanceled: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    val newDiet = Diet(
                        id = 0,
                        name = name,
                        weight = weight.toDoubleOrNull() ?: 0.0,
                        calorie = 0.0,
                        protein = 0.0,
                        fat = 0.0,
                        carbohydrate = 0.0,
                        createAt = LocalDate.now(),
                        type = ""
                    )
                    onDietAdded(newDiet)
                },
                enabled = name.isNotEmpty() && weight.isNotEmpty()
            ) {
                Text(stringResource(R.string.add_food))
            }

            Button(onClick = onCanceled) {
                Text(stringResource(R.string.cancel))
            }
        }

    }
}

@Preview
@Composable
fun AddDietFormPreview() {
    AddDietForm({ }, {})
}