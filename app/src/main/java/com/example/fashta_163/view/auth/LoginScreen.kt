package com.example.fashta_163.view.auth

import androidx.compose.foundation.background
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
import com.example.fashta_163.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


val FashionCream = Color(0xFFF9F5F0)
val FashionBrown = Color(0xFF5D4037)
val FashionSurface = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState = viewModel.uiStateAuth
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = FashionCream // Background Cream
    ) { innerPadding ->
        LoginBody(
            detailAuth = uiState.detailAuth,
            isEntryValid = uiState.isEntryValid,
            errorMessage = uiState.errorMessage,
            onValueChange = viewModel::updateUiState,
            onLoginClick = {
                coroutineScope.launch {
                    val success = viewModel.login()
                    if (success) onLoginSuccess()
                }
            },
            onRegisterClick = onNavigateToRegister,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun LoginBody(
    detailAuth: DetailAuth,
    isEntryValid: Boolean,
    errorMessage: String?,
    onValueChange: (DetailAuth) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Selamat Datang",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = FashionBrown
            )
        )
        Text(
            text = "Silakan login untuk melanjutkan",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )


        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = detailAuth.username,
                onValueChange = { onValueChange(detailAuth.copy(username = it)) },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FashionBrown,
                    focusedLabelColor = FashionBrown,
                    cursorColor = FashionBrown,
                    focusedContainerColor = FashionSurface,
                    unfocusedContainerColor = FashionSurface
                )
            )


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
                    focusedBorderColor = FashionBrown,
                    focusedLabelColor = FashionBrown,
                    cursorColor = FashionBrown,
                    focusedContainerColor = FashionSurface,
                    unfocusedContainerColor = FashionSurface
                ),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null, tint = FashionBrown)
                    }
                }
            )
        }


        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Buttons ---
        Button(
            onClick = onLoginClick,
            enabled = isEntryValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = FashionBrown,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text("Masuk", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Belum punya akun?", color = Color.Gray, fontSize = 14.sp)
            TextButton(onClick = onRegisterClick) {
                Text(
                    "Daftar",
                    color = FashionBrown,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}