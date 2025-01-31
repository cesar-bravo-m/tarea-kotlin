package com.example.myapplication.ui.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.business.UserManager
import com.example.myapplication.R
import com.example.myapplication.ui.components.AvatarPicker

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    context: Context,
    onLogout: () -> Unit
) {
    val currentUser = UserManager.getCurrentUser()
    if (currentUser == null) {
        onLogout()
        return
    }

    var fullName by remember { mutableStateOf(currentUser.fullName) }
    var email by remember { mutableStateOf(currentUser.email) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var selectedAvatar by remember { mutableStateOf(currentUser.avatar) }
    var showPasswordSection by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        AvatarPicker(
            selectedAvatar = selectedAvatar ?: R.raw.animatedmale1,
            onAvatarSelected = { selectedAvatar = it }
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        if (!showPasswordSection) {
            TextButton(onClick = { showPasswordSection = true }) {
                Text("Cambiar contraseña")
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Contraseña actual") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nueva contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it },
                        label = { Text("Confirmar nueva contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (showPasswordSection) {
                    if (newPassword != confirmNewPassword) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (!UserManager.authenticate(email, currentPassword)) {
                        Toast.makeText(context, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                }
                
                if (UserManager.updateProfile(
                    email = email,
                    newPassword = if (showPasswordSection) newPassword else null,
                    fullName = fullName,
                    avatar = selectedAvatar
                )) {
                    Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                    showPasswordSection = false
                    currentPassword = ""
                    newPassword = ""
                    confirmNewPassword = ""
                } else {
                    Toast.makeText(context, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Cerrar sesión")
        }
    }
} 