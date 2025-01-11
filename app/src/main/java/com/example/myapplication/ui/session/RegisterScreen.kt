package com.example.myapplication.ui.session

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.business.UserManager
import com.example.myapplication.ui.components.AvatarPicker

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RegisterScreen(
    innerPadding: PaddingValues,
    setIsLoggedIn: (Boolean) -> Unit,
    setShowRegister: (Boolean) -> Unit,
    context: Context
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedAvatar by remember { mutableStateOf(R.drawable.female1) }
    var currentStep by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (currentStep > 1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { currentStep-- }) {
                    Icon(Icons.Default.ArrowBack, "Volver")
                }
            }
        }

        LinearProgressIndicator(
            progress = currentStep / 3f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                slideInHorizontally { width -> width } with
                slideOutHorizontally { width -> -width }
            }
        ) { step ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                when (step) {
                    1 -> {
                        Text(
                            text = "¿Cómo quires que te llamemos?",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("Nombre o apodo") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Text(
                            text = "Elige un avatar",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        AvatarPicker(
                            selectedAvatar = selectedAvatar,
                            onAvatarSelected = { selectedAvatar = it }
                        )
                    }
                    2 -> {
                        Text(
                            text = "¿Cuál es tu email?",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    3 -> {
                        Text(
                            text = "Elige una contraseña",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirmar contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Button(
                    onClick = {
                        when (currentStep) {
                            1 -> {
                                if (fullName.isBlank()) {
                                    Toast.makeText(context, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                currentStep++
                            }
                            2 -> {
                                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                    Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                currentStep++
                            }
                            3 -> {
                                if (password.isBlank()) {
                                    Toast.makeText(context, "Por favor, ingresa una contraseña", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (password != confirmPassword) {
                                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (UserManager.register(email, password, fullName, selectedAvatar)) {
                                    Toast.makeText(context, "¡Registro exitoso! Inicia sesión con tu contraseña", Toast.LENGTH_SHORT).show()
                                    setShowRegister(false)
                                } else {
                                    Toast.makeText(context, "El email ya existe", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (currentStep < 3) "Siguiente" else "Crear cuenta")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = { setShowRegister(false) }
        ) {
            Text("¿Ya tienes una cuenta? Inicia sesión")
        }
    }
} 