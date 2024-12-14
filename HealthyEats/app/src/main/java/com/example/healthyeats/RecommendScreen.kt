package com.example.healthyeats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.healthyeats.component.AddDietForm
import com.example.healthyeats.component.RecommendCardList
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun RecommendScreen(viewModel: MainViewModel) {
    HealthyEatsTheme {
        var showAddDietDialog by remember { mutableStateOf(false) }
        var selectItem by remember { mutableStateOf<Recipe?>(null) }
        val recipeList: List<Recipe> = viewModel.recommendRecipes.collectAsState().value
        Column(modifier = Modifier.fillMaxSize()) {
            RecommendCardList(
                cardItems = recipeList,
                addToDiet = { cardItem ->
                    showAddDietDialog = true
                    selectItem = cardItem
                }
            )
            if (showAddDietDialog) {
                AlertDialog(
                    onDismissRequest = { showAddDietDialog = false },
                    title = { Text(stringResource(R.string.add_to_diet)) },
                    text = {
                        AddDietForm(recipeName = selectItem!!.title,
                            onDietAdded = { newDiet, selectTypeList ->
                                for (nt in selectItem!!.nutrition[MainViewModel.KEY_NUTRIENTS]!!) {
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
                                //add to diet
                                for (type in selectTypeList) {
                                    val diet = newDiet.copy()
                                    diet.type = type
                                    viewModel.addDiet(diet)
                                }
                                showAddDietDialog = false
                            },
                            onCanceled = {
                                showAddDietDialog = false
                            },
                            selectable = true,
                            viewModel = viewModel,
                            autocomplete = false
                        )
                    },
                    confirmButton = {
                        //not use
                    })
            }
        }
    }
}