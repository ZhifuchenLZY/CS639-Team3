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
import coil.compose.AsyncImage
import com.example.healthyeats.R
import com.example.healthyeats.ui.theme.HealthyEatsTheme

data class RecommendCardItem(
    val title: String,
    val imageUrl: String,
    val nutritionList: List<NutritionItem>
)

@Composable
fun RecommendCard(cardItem: RecommendCardItem, onDeleteClick: () -> Unit) {
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
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Delete",
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
                    model = cardItem.imageUrl,
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
                    cardItem.nutritionList.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = item.nutrition)
                            Text(text = "%.2f".format(item.ideal))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendCardList(cardItems: List<RecommendCardItem>, onCardDelete: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(cardItems.size) { index ->
            val cardItem = cardItems[index]
            RecommendCard(
                cardItem = cardItem,
                onDeleteClick = { onCardDelete(index) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendCardPreview() {
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