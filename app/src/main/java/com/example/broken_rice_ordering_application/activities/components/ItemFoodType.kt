package com.example.broken_rice_ordering_application.activities.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.rememberImagePainter
import com.example.broken_rice_ordering_application.R
import com.example.broken_rice_ordering_application.model.FoodType

@Composable
fun ItemFoodType(
    index: Int,
    foodType: FoodType,
    status: String,
    onClickHandle: (id: String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color("#2F2D2D".toColorInt())),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(bottom = 7.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = index.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(15.dp))

            Image(
                painter = rememberImagePainter(
                    data = foodType.image,
                    builder = {
                        placeholder(R.drawable.img)
                        error(R.drawable.img)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = foodType.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { onClickHandle(foodType.id) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    if (status == "edit") {
                        Icons.Default.Edit
                    } else {
                        Icons.Outlined.Delete
                    },
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}