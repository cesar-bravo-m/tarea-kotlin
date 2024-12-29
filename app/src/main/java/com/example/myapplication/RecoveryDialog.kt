package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun RecoveryDialog(
    setShowRecoveryDialog: (Boolean) -> Unit,
    setRecoveryStep: (Int) -> Unit,
    recoveryStep: Int,
    email: String,
    setEmail: (String) -> Unit,
    verificationCode: String,
    setVerificationCode: (String) -> Unit,
    context: Context
) {
    Dialog(onDismissRequest = {
        setShowRecoveryDialog(false)
        setRecoveryStep(1)
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (recoveryStep == 1) "Enter Email" else "Enter Verification Code",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (recoveryStep == 1) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { setEmail(it) },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                            if (email.isNotEmpty()) {
                                setRecoveryStep(2)
                                Toast.makeText(
                                    context,
                                    "Verification code sent to email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Send Code")
                    }
                } else {
                    OutlinedTextField(
                        value = verificationCode,
                        onValueChange = { setVerificationCode(it) },
                        label = { Text("Verification Code") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                            if (verificationCode == "000") {
                                setShowRecoveryDialog(false)
                                setRecoveryStep(1)
                                Toast.makeText(
                                    context,
                                    "Password reset successful! Check your email",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(context, "Invalid code. Try 000", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Verify Code")
                    }
                }

                TextButton(
                    onClick = {
                        setShowRecoveryDialog(false)
                        setRecoveryStep(1)
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
