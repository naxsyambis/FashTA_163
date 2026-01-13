package com.example.fashta_163.view.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fashta_163.modeldata.DetailAuth
import com.example.fashta_163.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff



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
        topBar = {
            TopAppBar(
                title = { Text("Login") }
            )
        }
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

            var passwordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = detailAuth.password,
                onValueChange = {
                    onValueChange(detailAuth.copy(password = it))
                },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    val icon =
                        if (passwordVisible) Icons.Filled.VisibilityOff
                        else Icons.Filled.Visibility

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible)
                                "Sembunyikan password"
                            else
                                "Lihat password"
                        )
                    }
                }
            )

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }


            Button(
                onClick = onLoginClick,
                enabled = isEntryValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            TextButton(onClick = onRegisterClick) {
                Text("Belum punya akun? Daftar")
            }
        }
    }
}
