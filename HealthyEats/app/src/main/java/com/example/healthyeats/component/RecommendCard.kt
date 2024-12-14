package com.example.healthyeats.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.healthyeats.MainViewModel
import com.example.healthyeats.R
import com.example.healthyeats.Recipe
import com.example.healthyeats.ui.theme.HealthyEatsTheme


@Composable
fun RecommendCard(cardItem: Recipe, addToDiet: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_recommend),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = cardItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = addToDiet) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add to diet",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Content
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Image
                AsyncImage(
                    model = cardItem.image,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Nutrition Table
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    cardItem.nutrition[MainViewModel.KEY_NUTRIENTS]!!.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = item.name)
                            Text(text = "%.1f".format(item.amount))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Hint: Nutrition is in g/100g. Calories in KCAL.", fontSize = 12.sp)
        }

    }
}

@Composable
fun RecommendCardList(cardItems: List<Recipe>, addToDiet: (Recipe) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(cardItems.size) { index ->
            val cardItem = cardItems[index]
            RecommendCard(
                cardItem = cardItem,
                addToDiet = { addToDiet(cardItem) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendCardPreview() {
    HealthyEatsTheme {
//        val sampleData = listOf(
//
//        )
//        RecommendCardList(
//            cardItems = sampleData,
//            onCardDelete = { index ->
//                // Handle delete action here
//                println("Deleting card at index $index")
//            }
//        )
    }
}