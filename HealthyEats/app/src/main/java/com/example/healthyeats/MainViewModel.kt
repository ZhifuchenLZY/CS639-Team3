package com.example.healthyeats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthyeats.spoonacular.SpoonacularApi
import com.example.healthyeats.spoonacular.SpoonacularRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    companion object {
        const val TYPE_BREAKFAST = "Breakfast"
        const val TYPE_LUNCH = "Lunch"
        const val TYPE_DINNER = "Dinner"

        const val KEY_PROTEIN = "Protein"
        const val KEY_FAT = "Fat"
        const val KEY_CARBO = "Carbohydrates"
        const val KEY_CALORIES = "Calories"
        const val KEY_NUTRIENTS = "nutrients"

        //Spoonacular api key
//        const val API_KEY = "8c311f82aff946f590441faa8830f3ed"
        const val API_KEY = "66245de305c148b68493a34bf299c69e"
    }

    var email = ""

    private val _dietList = MutableStateFlow<List<Diet>>(emptyList())

    private val api = SpoonacularApi.create("https://api.spoonacular.com")
    private val repository = SpoonacularRepository(api)

    //id index
    var idIndex = 0

    //breakfast
    val breakfastItems: StateFlow<List<Diet>> = _dietList.map { dietList ->
        dietList.filter { it.type == TYPE_BREAKFAST }
    }.stateIn(
        scope = viewModelScope,
        //share when changed
        started = SharingStarted.WhileSubscribed(1L),
        initialValue = emptyList()
    )

    //lunch
    val lunchItems: StateFlow<List<Diet>> = _dietList.map { dietList ->
        dietList.filter { it.type == TYPE_LUNCH }
    }.stateIn(
        scope = viewModelScope,
        //share when changed
        started = SharingStarted.WhileSubscribed(1L),
        initialValue = emptyList()
    )

    //dinner
    val dinnerItems: StateFlow<List<Diet>> = _dietList.map { dietList ->
        dietList.filter { it.type == TYPE_DINNER }
    }.stateIn(
        scope = viewModelScope,
        //share when changed
        started = SharingStarted.WhileSubscribed(1L),
        initialValue = emptyList()
    )

    private fun calculateNutritionStats(dietList: List<Diet>): Map<String, Double> {
        return mapOf(
            KEY_PROTEIN to dietList.sumOf { it.protein },
            KEY_FAT to dietList.sumOf { it.fat },
            KEY_CARBO to dietList.sumOf { it.carbohydrate },
            KEY_CALORIES to dietList.sumOf { it.calories }
        )
    }

    //stats nutrition
    val nutritionStats: StateFlow<Map<String, Double>> = _dietList.map {
        calculateNutritionStats(_dietList.value)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1L),
        initialValue = calculateNutritionStats(_dietList.value)
    )

    //recommend recipes list
    private val _recommendRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recommendRecipes: StateFlow<List<Recipe>> = _recommendRecipes

    private val _autoRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val autoRecipes: StateFlow<List<Recipe>> = _autoRecipes

    // Add a new diet item to the list
    fun addDiet(diet: Diet) {
        //set diet id
        diet.id = idIndex
        idIndex += 1
        _dietList.value += diet
    }

    // Remove a diet item from the list
    fun removeDietByID(id: Int) {
        val currentList = _dietList.value.toMutableList() ?: return
        _dietList.value = currentList.filter { it.id != id }
    }

    //remove diet list
    fun removeDietList(dietListRemoved: List<Diet>) {
        viewModelScope.launch {
            _dietList.update { currentList ->
                currentList.filterNot { diet -> dietListRemoved.any { it.id == diet.id } }
            }
        }
    }

    // Update an existing diet item in the list
    fun updateDiet(updatedDiet: Diet) {
        val currentList = _dietList.value.toMutableList() ?: return
        val index = currentList.indexOfFirst { it.id == updatedDiet.id }
        if (index != -1) {
            currentList[index] = updatedDiet
            _dietList.value = currentList
        }
    }

    // Find a diet item by ID
    fun findDietById(id: Int): Diet? {
        return _dietList.value.find { it.id == id }
    }

    // Clear all diet items from the list
    fun clearAllDiets() {
        _dietList.value = listOf()
    }

    /**
     * fetch recommend recipes from web api
     */
    fun fetchRecipes(filter: RecipeFilter) {
        viewModelScope.launch {
            val response = repository.getRecipes(API_KEY, filter)
            if (response.isSuccess) {
                val recipeSearchResponse = response.getOrNull()
                if (recipeSearchResponse != null) {
                    _recommendRecipes.update {
                        recipeSearchResponse.results
                    }
                }
                Log.e("TAG", "fetchRecipes: " + Gson().toJson(recipeSearchResponse))
            }
        }
    }

    /**
     * autocomplete recipes
     */
    fun autoCompleteRecipes(filter: RecipeFilter) {
        _autoRecipes.update { emptyList() }
        viewModelScope.launch {
            val response = repository.getRecipes(API_KEY, filter)
            if (response.isSuccess) {
                val autoRecipesResponse = response.getOrNull()
                if (autoRecipesResponse != null) {
                    _autoRecipes.update {
                        autoRecipesResponse.results
                    }
                }
                Log.e("TAG", "autoCompleteRecipes: " + Gson().toJson(autoRecipesResponse))
            }
        }
    }
}