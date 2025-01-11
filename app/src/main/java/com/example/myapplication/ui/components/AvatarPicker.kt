package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun AvatarPicker(
    selectedAvatar: Int,
    onAvatarSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val avatars = listOf(
            R.drawable.female1,
            R.drawable.female2,
            R.drawable.male1,
            R.drawable.male2
        )

        avatars.forEach { avatar ->
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 3.dp,
                        color = if (selectedAvatar == avatar)
                            Color(0xFF4CAF50) // Material Green
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onAvatarSelected(avatar) }
            ) {
                Image(
                    painter = painterResource(id = avatar),
                    contentDescription = "Avatar option",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (selectedAvatar == avatar) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x77000000)), // Semi-transparent black overlay
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
} 