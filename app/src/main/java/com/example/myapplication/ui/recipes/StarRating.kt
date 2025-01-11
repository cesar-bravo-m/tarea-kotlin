package com.example.myapplication.ui.recipes

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StarRating(
    score: Int,
    modifier: Modifier = Modifier
) {
    val stars = (score * 5) / 100

    Row(modifier = modifier) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < stars) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (index < stars) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
    }
} 