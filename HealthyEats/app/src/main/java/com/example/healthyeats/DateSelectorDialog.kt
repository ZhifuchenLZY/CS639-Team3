package com.example.healthyeats

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateSelectorDialog(
    currentDate: String,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(currentDate) }

    // Create MaterialDatePicker
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()

    // Show the date picker
    LaunchedEffect(key1 = true) {
        (context as? FragmentActivity)?.supportFragmentManager?.let {
            datePicker.show(
                it,
                context.getString(R.string.date_picker)
            )
        }
        datePicker.addOnPositiveButtonClickListener {
            val date = Date(it)
            val dateFormat =
                SimpleDateFormat(context.getString(R.string.date_format), Locale.getDefault())
            selectedDate = dateFormat.format(date)
            onDateSelected(selectedDate)
        }
    }

    // Dialog content
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.select_date)) },
        text = { Text(stringResource(R.string.selected_date, selectedDate)) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.confirm))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DateSelectorDialogPreview() {
    // Mock the current date for preview purposes
    val currentDate = "2024-12-05"

    // Mock the action that should occur when a date is selected
    val onDateSelected: (String) -> Unit = { newDate ->
        // In a real application, this would update the UI or perform other logic
        println("Date Selected: $newDate")
    }

    // Mock the action that should occur when the dialog is dismissed
    val onDismiss: () -> Unit = {
        // In a real application, this would close the dialog
        println("Dialog Dismissed")
    }

    // Call the DateSelectorDialog composable function with the mocked parameters
    DateSelectorDialog(
        currentDate = currentDate,
        onDateSelected = onDateSelected,
        onDismiss = onDismiss
    )
}