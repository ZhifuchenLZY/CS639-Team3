package com.example.healthyeats.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.healthyeats.Diet
import com.example.healthyeats.MainViewModel
import com.example.healthyeats.R
import com.example.healthyeats.Recipe
import com.example.healthyeats.RecipeFilter

@Composable
fun CheckboxWithLabel(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    onValueChange: (Recipe?) -> Unit,
    viewModel: MainViewModel,
    recipeName: String = "",
    autocomplete: Boolean = false
) {
    // State to store the current selected value and whether the dropdown is expanded
    var expanded by remember { mutableStateOf(false) }
    var inputRecipe by remember { mutableStateOf(recipeName) }

    // Collect the auto-completed recipes from the ViewModel
    val recipes = viewModel.autoRecipes.collectAsState()

    // ExposedDropdownMenuBox wraps the OutlinedTextField and ExposedDropdownMenu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = false },
        modifier = modifier
    ) {
        // Row to place the TextField and Button side by side
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            // OutlinedTextField for user input
            OutlinedTextField(
                value = inputRecipe,
                onValueChange = {
                    inputRecipe = it
                    //clear select recipe
                    onValueChange(null)
                },
                label = label,
                trailingIcon = {
                    if (expanded) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = ("")
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ("")
                        )
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .menuAnchor()  // Ensure the dropdown menu aligns with the TextField
            )

            // Button to toggle the dropdown menu
            Button(
                onClick = {
                    if (inputRecipe.isNotEmpty()) {
                        //search recipes by user input
                        val filter = RecipeFilter(query = inputRecipe, number = 5)
                        viewModel.autoCompleteRecipes(filter)
                        expanded = true
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Search")
            }
        }

        // ExposedDropdownMenu to display the filtered options
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            recipes.value.forEach { recipe ->
                DropdownMenuItem(
                    text = { Text(text = recipe.title) },
                    onClick = {
                        inputRecipe = recipe.title
                        expanded = false
                        onValueChange(recipe)  // Pass the selected option to the parent component
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }

            // Display a message if no options match the input
            if (recipes.value.isEmpty()) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.no_matches_found)) },
                    onClick = { /* Do nothing */ },
                    enabled = false
                )
            }
        }
    }
}

@Composable
fun AddDietForm(
    onDietAdded: (Diet, List<String>) -> Unit,
    onCanceled: () -> Unit,
    selectable: Boolean = false,
    recipeName: String = "",
    viewModel: MainViewModel,
    autocomplete: Boolean
) {
    var weight by remember { mutableStateOf("") }
    var isBreakfastChecked by remember { mutableStateOf(false) }
    var isLunchChecked by remember { mutableStateOf(false) }
    var isDinnerChecked by remember { mutableStateOf(false) }
    var selectTypeList by remember { mutableStateOf(emptyList<String>()) }
    var inputRecipe by remember { mutableStateOf<Recipe?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AutoCompleteTextField(
            viewModel = viewModel,
            autocomplete = autocomplete,
            label = { Text(stringResource(R.string.name)) },
            onValueChange = { recipe ->
                inputRecipe = recipe
            },
            recipeName = recipeName
        )

//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = name,
//            onValueChange = { name = it },
//            label = { Text(stringResource(R.string.name)) }
//        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = weight,
            onValueChange = { weight = it },
            label = { Text(stringResource(R.string.weight_g)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        if (selectable) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Breakfast Checkbox
                CheckboxWithLabel(
                    checked = isBreakfastChecked,
                    onCheckedChange = {
                        isBreakfastChecked = it
                    },
                    label = stringResource(R.string.breakfast)
                )

                // Lunch Checkbox
                CheckboxWithLabel(
                    checked = isLunchChecked,
                    onCheckedChange = { isLunchChecked = it },
                    label = stringResource(R.string.lunch)
                )

                // Dinner Checkbox
                CheckboxWithLabel(
                    checked = isDinnerChecked,
                    onCheckedChange = { isDinnerChecked = it },
                    label = stringResource(R.string.dinner)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            //save diet
            Button(
                onClick = {
                    val newDiet = Diet(
                        name = "",
                        weight = weight.toDoubleOrNull() ?: 0.0
                    )
                    if (autocomplete) {
                        newDiet.name = inputRecipe!!.title
                        //create new diet
                        for (nt in inputRecipe!!.nutrition[MainViewModel.KEY_NUTRIENTS]!!) {
                            if (nt.name == MainViewModel.KEY_FAT) {
                                newDiet.fat = nt.amount * newDiet.weight / 100
                            }
                            if (nt.name == MainViewModel.KEY_PROTEIN) {
                                newDiet.protein = nt.amount * newDiet.weight / 100
                            }
                            if (nt.name == MainViewModel.KEY_CARBO) {
                                newDiet.carbohydrate = nt.amount * newDiet.weight / 100
                            }
                            if (nt.name == MainViewModel.KEY_CALORIES) {
                                newDiet.calories = nt.amount * newDiet.weight / 100
                            }
                        }
                    } else {
                        newDiet.name = recipeName
                        if (isBreakfastChecked) {
                            selectTypeList += MainViewModel.TYPE_BREAKFAST
                        }
                        if (isLunchChecked) {
                            selectTypeList += MainViewModel.TYPE_LUNCH
                        }
                        if (isDinnerChecked) {
                            selectTypeList += MainViewModel.TYPE_DINNER
                        }
                    }
                    onDietAdded(newDiet, selectTypeList)
                },
                enabled = weight.isNotEmpty()
                        && (isBreakfastChecked || isLunchChecked || isDinnerChecked || !selectable)
                        && (inputRecipe != null || !autocomplete)
            ) {
                Text(stringResource(R.string.add))
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

}