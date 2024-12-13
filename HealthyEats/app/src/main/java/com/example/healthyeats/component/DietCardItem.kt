package com.example.healthyeats.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthyeats.R
import com.example.healthyeats.Diet


@Composable
fun DietCardItem(
    icon: Int,
    title: String,
    items: List<Diet>,
    onAddClick: () -> Unit,
    onDeleteClick: (List<Diet>) -> Unit
) {
    var selectedItems by remember { mutableStateOf(listOf<Diet>()) }

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
                    IconButton(
                        onClick = { onDeleteClick(selectedItems) },
                        enabled = selectedItems.isNotEmpty()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = stringResource(R.string.delete),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            val textModifier = Modifier
                .weight(1f)
                .widthIn(max = Int.MAX_VALUE.dp)
                .padding(4.dp)
            //title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.food), modifier = textModifier
                )
                Text(
                    text = stringResource(R.string.weight), modifier = textModifier
                )
                Text(
                    text = stringResource(R.string.calories), modifier = textModifier
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
            // food list
            LazyColumn(modifier = Modifier.padding(top = 4.dp)) {
                items(items) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.name, modifier = textModifier, softWrap = true
                        )
                        Text(
                            text = item.weight.toString(), modifier = textModifier, softWrap = true
                        )
                        Text(
                            text = item.calorie.toString(), modifier = textModifier, softWrap = true
                        )
                        //select to delete
                        Checkbox(
                            checked = selectedItems.contains(item),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedItems += item
                                } else {
                                    selectedItems -= item
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardItemPreview() {
    DietCardItem(
        icon = R.drawable.ic_dinner,
        title = "Card Title",
        items = listOf(),
        onAddClick = {},
        onDeleteClick = {}
    )
}