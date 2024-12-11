package com.example.healthyeats

import java.time.LocalDate

data class Diet(
    val id: Int,                // Unique identifier for the diet item
    val name: String,           // The name of the food item or meal
    val weight: Double,         // Weight of the food item in grams
    val calorie: Double,        // Caloric content of the food item
    val protein: Double,        // Protein content in grams
    val fat: Double,            // Fat content in grams
    val carbohydrate: Double,   // Carbohydrate content in grams
    val createAt: LocalDate,          // date
    val type: String
)
