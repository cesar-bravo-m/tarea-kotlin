package com.example.myapplication.ui.session

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapplication.R

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    setShowRecoveryDialog: (Boolean) -> Unit,
    setRecoveryStep: (Int) -> Unit,
    setEmail: (String) -> Unit,
    setVerificationCode: (String) -> Unit,
    setIsLoggedIn: (Boolean) -> Unit,
    setShowRegister: (Boolean) -> Unit,
    showRegister: Boolean,
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
        var isAnimationFinished by remember { mutableStateOf(false) }
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.foodlogo),
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1,
            isPlaying = true
        )
        LaunchedEffect(progress) {
            if (progress == 1f) {
                isAnimationFinished = true
            }
        }
        Crossfade(targetState = isAnimationFinished, animationSpec = tween(durationMillis = 1000)) { showImage ->
            if (showImage) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(240.dp)
                )
            } else {
                LottieAnimation(
                    composition = composition,
                    iterations = 1,
                    modifier = Modifier.size(240.dp),
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }

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
            Text(text="Crear cuenta", fontSize=18.sp)
        }

        TextButton(
            onClick = {
                setShowRecoveryDialog(true)
                setRecoveryStep(1)
                setEmail("")
                setVerificationCode("")
            }
        ) {
            Text("Recuperar contraseña", fontSize=18.sp)
        }
    }
}