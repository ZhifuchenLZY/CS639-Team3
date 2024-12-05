package com.example.healthyeats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardItem(
    icon: Int,
    title: String,
    items: List<String>,
    onAddClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                }

                Row {
                    IconButton(onClick = onAddClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(R.string.add),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = stringResource(R.string.delete),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Content list
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(items) { item ->
                    Text(text = item, modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun CardItemPreview() {
    CardItem(
        icon = R.drawable.ic_dinner,
        title = "Card Title",
        items = listOf("Item 1", "Item 2", "Item 3"),
        onAddClick = {},
        onDeleteClick = {}
    )
}