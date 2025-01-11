package com.example.myapplication.ui.session

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.business.UserManager

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    setShowRecoveryDialog: (Boolean) -> Unit,
    setRecoveryStep: (Int) -> Unit,
    setEmail: (String) -> Unit,
    setVerificationCode: (String) -> Unit,
    setIsLoggedIn: (Boolean) -> Unit,
    setShowRegister: (Boolean) -> Unit,
    context: Context,
) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (selectedProfile, setSelectedProfile) = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sabor Diario",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        UserCard(
            setEmail = setEmail,
            setPassword = setPassword,
            selectedProfile = selectedProfile,
            setSelectedProfile = setSelectedProfile,
            setIsLoggedIn = setIsLoggedIn,
            context = context
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { setShowRegister(true) }
        ) {
            Text("Crear cuenta")
        }
        // if (UserManager.getUsersWhoPreviouslyHaveLoggedIn().isEmpty()) {
        // } else {
        //     TextButton(
        //         onClick = { setShowRegister(true) }
        //     ) {
        //         Text("¿No tienes cuenta? Regístrate")
        //     }
        // }


        TextButton(
            onClick = {
                setShowRecoveryDialog(true)
                setRecoveryStep(1)
                setEmail("")
                setVerificationCode("")
            }
        ) {
            Text("Recuperar contraseña")
        }
    }
}