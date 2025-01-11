package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.ui.recipes.RecipeList
import com.example.myapplication.ui.session.LoginScreen
import com.example.myapplication.ui.session.RecoveryDialog
import com.example.myapplication.ui.session.RegisterScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.navigation.Destination
import com.example.myapplication.ui.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentDestination by remember { mutableStateOf(Destination.RECIPES) }

    if (!isLoggedIn) {
        var showRegister by remember { mutableStateOf(false) }
        var showRecoveryDialog by remember { mutableStateOf(false) }
        var recoveryStep by remember { mutableStateOf(1) }
        var email by remember { mutableStateOf("") }
        var verificationCode by remember { mutableStateOf("") }

        if (showRegister) {
            RegisterScreen(
                innerPadding = PaddingValues(),
                setIsLoggedIn = { isLoggedIn = it },
                setShowRegister = { showRegister = it },
                context = LocalContext.current
            )
        } else {
            LoginScreen(
                innerPadding = PaddingValues(),
                setShowRecoveryDialog = { showRecoveryDialog = it },
                setRecoveryStep = { recoveryStep = it },
                setEmail = { email = it },
                setVerificationCode = { verificationCode = it },
                setIsLoggedIn = { isLoggedIn = it },
                setShowRegister = { showRegister = it },
                context = LocalContext.current
            )
        }

        if (showRecoveryDialog) {
            RecoveryDialog(
                setShowRecoveryDialog = { showRecoveryDialog = false },
                recoveryStep = recoveryStep,
                setRecoveryStep = { recoveryStep = it },
                email = email,
                setEmail = { email = it },
                verificationCode = verificationCode,
                setVerificationCode = { verificationCode = it },
                context = LocalContext.current
            )
        }
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination == Destination.RECIPES,
                        onClick = { currentDestination = Destination.RECIPES },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Planificador"
                            )
                        },
                        label = { Text("Recetas") }
                    )
                    NavigationBarItem(
                        selected = currentDestination == Destination.PROFILE,
                        onClick = { currentDestination = Destination.PROFILE },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Perfil"
                            )
                        },
                        label = { Text("Perfil") }
                    )
                }
            }
        ) { scaffoldPadding ->
            when (currentDestination) {
                Destination.RECIPES -> RecipeList(
                    innerPadding = scaffoldPadding
                )
                Destination.PROFILE -> ProfileScreen(
                    innerPadding = scaffoldPadding,
                    context = LocalContext.current,
                    onLogout = { isLoggedIn = false }
                )
            }
        }
    }
}