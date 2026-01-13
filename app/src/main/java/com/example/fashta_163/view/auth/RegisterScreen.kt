package com.example.fashta_163.view.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fashta_163.modeldata.DetailAuth
import com.example.fashta_163.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

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
        topBar = {
            TopAppBar(
                title = { Text("Register") }
            )
        }
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
    Box(
        modifier = modifier.padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = detailAuth.username,
                onValueChange = {
                    onValueChange(detailAuth.copy(username = it))
                },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = detailAuth.password,
                onValueChange = {
                    onValueChange(detailAuth.copy(password = it))
                },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onRegisterClick,
                enabled = isEntryValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Daftar")
            }

            TextButton(onClick = onBackToLoginClick) {
                Text("Sudah punya akun? Login")
            }
        }
    }
}
