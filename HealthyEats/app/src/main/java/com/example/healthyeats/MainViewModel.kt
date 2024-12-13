package com.example.healthyeats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthyeats.component.RecommendCardItem
import com.example.healthyeats.fat_secret.foodList
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
    }

    private val _dietList = MutableStateFlow<List<Diet>>(emptyList())

    //id index
    private var idIndex = 0

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

    val recommendFood = MutableStateFlow<List<RecommendCardItem>>(foodList)

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

}