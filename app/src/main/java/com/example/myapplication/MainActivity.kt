package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val (showRecoveryDialog, setShowRecoveryDialog) = remember { mutableStateOf(false) }
    val (recoveryStep, setRecoveryStep) = remember { mutableStateOf(1) }
    val (email, setEmail) = remember { mutableStateOf("") }
    val (verificationCode, setVerificationCode) = remember { mutableStateOf("") }
    val (isLoggedIn, setIsLoggedIn) = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (isLoggedIn) {
            RecipeList(innerPadding)
        } else if (!isLoggedIn && !showRecoveryDialog) {
            LoginScreen(
                innerPadding,
                setShowRecoveryDialog,
                setRecoveryStep,
                setEmail,
                setVerificationCode,
                setIsLoggedIn,
                context
            )
        } else if (!isLoggedIn && showRecoveryDialog) {
            RecoveryDialog(
                setShowRecoveryDialog,
                setRecoveryStep,
                recoveryStep,
                email,
                setEmail,
                verificationCode,
                setVerificationCode,
                context
            )
        }
    }
}