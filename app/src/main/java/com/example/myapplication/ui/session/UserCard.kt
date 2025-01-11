package com.example.myapplication.ui.session

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.business.User
import com.example.myapplication.business.UserManager

@Composable
fun UserCard(
    setEmail: (String) -> Unit,
    setPassword: (String) -> Unit,
    selectedProfile: String,
    setSelectedProfile: (String) -> Unit,
) {
    val previousUsers = UserManager.getUsersWhoPreviouslyHaveLoggedIn()
    
    if (previousUsers.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 24.dp
            )
        ) {
            items(previousUsers) { user ->
                UserAvatar(user, setEmail, setPassword, selectedProfile, setSelectedProfile)
            }
        }
    }
}

@Composable
private fun UserAvatar(
    user: User,
    setEmail: (String) -> Unit,
    setPassword: (String) -> Unit,
    selectedProfile: String,
    setSelectedProfile: (String) -> Unit,
) {
    val isSelected = user.email == selectedProfile
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                setEmail(user.email)
                setPassword("admin")
                setSelectedProfile(user.email)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) 
                MaterialTheme.colorScheme.onPrimaryContainer 
            else 
                MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (user.avatar != null) {
                    Image(
                        painter= painterResource(id=user.avatar),
                        contentDescription="Avatar",
                        modifier=Modifier.fillMaxSize(),
                    )
                } else {
                    Text(
                        text = user.fullName.first().uppercase(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = user.fullName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}