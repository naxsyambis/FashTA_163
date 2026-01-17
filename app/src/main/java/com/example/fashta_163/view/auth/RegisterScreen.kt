package com.example.fashta_163.view.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fashta_163.modeldata.DetailAuth
import com.example.fashta_163.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

// --- SOLUSI: Nama warna diganti agar TIDAK BENTROK dengan file Login ---
private val RegisterBgColor = Color(0xFFF9F5F0)    // Cream
private val RegisterPrimaryColor = Color(0xFF5D4037) // Cokelat Tua
private val RegisterInputBg = Color(0xFFFFFFFF)    // Putih

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateBackToLogin: () -> Unit
) {
    val uiState = viewModel.uiStateAuth
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = RegisterBgColor // Menggunakan nama warna baru
    ) { innerPadding ->
        RegisterBody(
            detailAuth = uiState.detailAuth,
            isEntryValid = uiState.isEntryValid,
            onValueChange = viewModel::updateUiState,
            onRegisterClick = {
                coroutineScope.launch {
                    val success = viewModel.register()
                    if (success) onRegisterSuccess()
                }
            },
            onBackToLoginClick = onNavigateBackToLogin,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun RegisterBody(
    detailAuth: DetailAuth,
    isEntryValid: Boolean,
    onValueChange: (DetailAuth) -> Unit,
    onRegisterClick: () -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Header ---
        Text(
            text = "Buat Akun",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RegisterPrimaryColor // Pakai warna baru
            )
        )
        Text(
            text = "Daftar untuk mulai mengelola stok",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // --- Input Fields ---
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Username
            OutlinedTextField(
                value = detailAuth.username,
                onValueChange = { onValueChange(detailAuth.copy(username = it)) },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RegisterPrimaryColor,
                    focusedLabelColor = RegisterPrimaryColor,
                    cursorColor = RegisterPrimaryColor,
                    focusedContainerColor = RegisterInputBg,
                    unfocusedContainerColor = RegisterInputBg
                )
            )

            // Password
            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = detailAuth.password,
                onValueChange = { onValueChange(detailAuth.copy(password = it)) },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RegisterPrimaryColor,
                    focusedLabelColor = RegisterPrimaryColor,
                    cursorColor = RegisterPrimaryColor,
                    focusedContainerColor = RegisterInputBg,
                    unfocusedContainerColor = RegisterInputBg
                ),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null, tint = RegisterPrimaryColor)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Buttons ---
        Button(
            onClick = onRegisterClick,
            enabled = isEntryValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RegisterPrimaryColor,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text("Daftar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Sudah punya akun?", color = Color.Gray, fontSize = 14.sp)
            TextButton(onClick = onBackToLoginClick) {
                Text(
                    "Login",
                    color = RegisterPrimaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}