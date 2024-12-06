package com.example.healthyeats

import androidx.compose.runtime.Composable
import com.example.healthyeats.component.NutritionItem
import com.example.healthyeats.component.RecommendCardItem
import com.example.healthyeats.component.RecommendCardList
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun RecommendScreen() {
    HealthyEatsTheme {
        val sampleData = listOf(
            RecommendCardItem(
                title = "Sample Card 1",
                imageUrl = "https://mmbiz.qpic.cn/sz_mmbiz_png/gtMexOnrtBZH5Gq7pvH0pmW9ENnXRo3iaNxFL3Iuq1onXQVvz1HNrcY4YIwt3zCSv9mmSicsWeFolJSPFq06J3Fw/640?wx_fmt=png&from=appmsg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1",
                nutritionList = listOf(
                    NutritionItem("Protein", 50.0f, 45.0f),
                    NutritionItem("Carbo", 100.0f, 95.0f),
                    NutritionItem("Fats", 70.0f, 75.0f),
                    NutritionItem("Fiber", 25.0f, 20.0f)
                )
            ),
            RecommendCardItem(
                title = "Sample Card 2",
                imageUrl = "https://example.com/image2.jpg",
                nutritionList = listOf(
                    NutritionItem("Vitamin A", 10.0f, 8.0f),
                    NutritionItem("Vitamin C", 20.0f, 18.0f),
                    NutritionItem("Calcium", 30.0f, 32.0f),
                    NutritionItem("Iron", 15.0f, 12.0f)
                )
            )
        )
        RecommendCardList(
            cardItems = sampleData,
            onCardDelete = { index ->
                // Handle delete action here
                println("Deleting card at index $index")
            }
        )
    }
}