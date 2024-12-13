package com.example.healthyeats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.healthyeats.component.RecommendCardItem
import com.example.healthyeats.component.RecommendCardList
import com.example.healthyeats.ui.theme.HealthyEatsTheme

@Composable
fun RecommendScreen(viewModel: MainViewModel) {
    HealthyEatsTheme {
        val foodList: List<RecommendCardItem> = viewModel.recommendFood.collectAsState().value
        RecommendCardList(
            cardItems = foodList,
            onCardDelete = { index ->
                // Handle delete action here
                println("Deleting card at index $index")
            }
        )
    }
}