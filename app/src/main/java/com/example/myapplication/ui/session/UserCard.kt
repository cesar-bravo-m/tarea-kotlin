package com.example.myapplication.ui.session

import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.business.User
import com.example.myapplication.business.UserManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun UserCard(
    setEmail: (String) -> Unit,
    setPassword: (String) -> Unit,
    selectedProfile: String,
    setSelectedProfile: (String) -> Unit,
    setIsLoggedIn: (Boolean) -> Unit,
    context: Context
) {
    var previousUsers by remember { mutableStateOf(UserManager.getUsersWhoPreviouslyHaveLoggedIn()) }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    
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
                UserAvatar(
                    user = user,
                    setEmail = setEmail,
                    setPassword = setPassword,
                    selectedProfile = selectedProfile,
                    setSelectedProfile = setSelectedProfile,
                    onCardClick = {
                        selectedUser = user
                        showPasswordDialog = true
                    },
                    onRemoveProfile = {
                        UserManager.removeFromUsersWhoHavePreviouslyLoggedIn(user.email)
                        previousUsers = UserManager.getUsersWhoPreviouslyHaveLoggedIn()
                        if (selectedProfile == user.email) {
                            setSelectedProfile("")
                        }
                    }
                )
            }
        }
    }

    if (showPasswordDialog && selectedUser != null) {
        PasswordDialog(
            email = selectedUser!!.email,
            onDismiss = { 
                showPasswordDialog = false
                selectedUser = null
                setSelectedProfile("")
            },
            onConfirm = { password ->
                if (UserManager.authenticate(selectedUser!!.email, password)) {
                    setIsLoggedIn(true)
                    Toast.makeText(context, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
                showPasswordDialog = false
                selectedUser = null
            }
        )
    }
}

@Composable
private fun UserAvatar(
    user: User,
    setEmail: (String) -> Unit,
    setPassword: (String) -> Unit,
    selectedProfile: String,
    setSelectedProfile: (String) -> Unit,
    onCardClick: () -> Unit,
    onRemoveProfile: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val isSelected = user.email == selectedProfile
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                setEmail(user.email)
                setSelectedProfile(user.email)
                onCardClick()
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

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones"
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Quitar perfil") },
                        onClick = {
                            onRemoveProfile()
                            showMenu = false
                        }
                    )
                }
            }
        }
    }
}