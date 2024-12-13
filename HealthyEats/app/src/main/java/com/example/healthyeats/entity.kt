package com.example.healthyeats

import java.time.LocalDate

/**
 * define diet object
 */
data class Diet(
    var id: Int,                // Unique identifier for the diet item
    var name: String,           // The name of the food item or meal
    var weight: Double,         // Weight of the food item in grams
    var calorie: Double,        // Caloric content of the food item
    var protein: Double,        // Protein content in grams
    var fat: Double,            // Fat content in grams
    var carbohydrate: Double,   // Carbohydrate content in grams
    var createAt: LocalDate,          // date
    var type: String
)

// Data class to hold nutrition information
data class NutritionItem(
    val nutrition: String,
    val ideal: Float,
    val real: Float
)