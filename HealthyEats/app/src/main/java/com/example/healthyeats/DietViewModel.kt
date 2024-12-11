package com.example.healthyeats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DietViewModel : ViewModel() {

    private val _dietList = MutableLiveData<List<Diet>>()
    val dietList: LiveData<List<Diet>>
        get() = _dietList

    init {
        // Initialize with an empty list
        _dietList.value = listOf()
    }

    // Add a new diet item to the list
    fun addDiet(diet: Diet) {
        val currentList = _dietList.value?.toMutableList() ?: mutableListOf()
        currentList.add(diet)
        _dietList.value = currentList
    }

    // Remove a diet item from the list
    fun removeDiet(id: Int) {
        val currentList = _dietList.value?.toMutableList() ?: return
        _dietList.value = currentList.filter { it.id != id }
    }

    // Update an existing diet item in the list
    fun updateDiet(updatedDiet: Diet) {
        val currentList = _dietList.value?.toMutableList() ?: return
        val index = currentList.indexOfFirst { it.id == updatedDiet.id }
        if (index != -1) {
            currentList[index] = updatedDiet
            _dietList.value = currentList
        }
    }

    // Find a diet item by ID
    fun findDietById(id: Int): Diet? {
        return _dietList.value?.find { it.id == id }
    }

    // Clear all diet items from the list
    fun clearAllDiets() {
        _dietList.value = listOf()
    }
}