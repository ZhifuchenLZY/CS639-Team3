package com.example.healthyeats.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

@Composable
fun MinMaxInput(
    modifier: Modifier = Modifier,
    minLabel: String = "Min:",
    maxLabel: String = "Max:",
    minValue: Double = 0.0,
    maxValue: Double = 10000.0
) {
    // Manage input state for min and max values
    var minText by remember { mutableStateOf(minValue.toString()) }
    var maxText by remember { mutableStateOf(maxValue.toString()) }

    // Track error states
    var isMinError by remember { mutableStateOf(false) }
    var isMaxError by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // Min value input field
        TextField(
            value = minText,
            onValueChange = { newText ->
                minText = newText
                isMinError = try {
                    val newMin = newText.toDoubleOrNull() ?: return@TextField
                    newMin > maxValue || newMin < 0
                } catch (e: NumberFormatException) {
                    true
                }
            },
            label = { Text(minLabel) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = isMinError,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        )

        if (isMinError) {
            Text(
                text = "Min value must be between 0 and max value.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        // Max value input field
        TextField(
            value = maxText,
            onValueChange = { newText ->
                maxText = newText
                isMaxError = try {
                    val newMax = newText.toDoubleOrNull() ?: return@TextField
                    newMax < minValue || newMax > 100
                } catch (e: NumberFormatException) {
                    true
                }
            },
            label = { Text(maxLabel) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = isMaxError,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        )

        if (isMaxError) {
            Text(
                text = "Max value must be greater than min value and <= 100.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun recommendFilter() {
    Card(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val textModifier = Modifier.defaultMinSize(minWidth = 96.dp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.protein),
                    modifier = textModifier
                )
                MinMaxInput()
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.fat),
                    modifier = textModifier
                )
                MinMaxInput()
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.carbohydrate),
                    modifier = textModifier
                )
                MinMaxInput()
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.calories),
                    modifier = textModifier
                )
                MinMaxInput()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMinMaxInput() {
    MinMaxInput(modifier = Modifier.fillMaxSize())
}

@Preview
@Composable
fun PreviewRecommendFilter() {
    recommendFilter()
}