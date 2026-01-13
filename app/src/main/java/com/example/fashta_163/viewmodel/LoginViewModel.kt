package com.example.fashta_163.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.DetailAuth
import com.example.fashta_163.modeldata.UIStateAuth
import com.example.fashta_163.modeldata.toDataAdmin
import com.example.fashta_163.repository.RepositoryAuth


class LoginViewModel(
    private val repositoryAuth: RepositoryAuth
) : ViewModel() {

    var uiStateAuth by mutableStateOf(UIStateAuth())
        private set

    fun updateUiState(detailAuth: DetailAuth) {
        uiStateAuth = UIStateAuth(
            detailAuth = detailAuth,
            isEntryValid = validasiInput(detailAuth)
        )
    }

    private fun validasiInput(detail: DetailAuth): Boolean =
        detail.username.isNotBlank() && detail.password.isNotBlank()

    suspend fun login(): Boolean {
        if (!uiStateAuth.isEntryValid) return false

        return try {
            val response = repositoryAuth.login(
                uiStateAuth.detailAuth.toDataAdmin()
            )

            if (response.status) {
                true
            } else {
                uiStateAuth = uiStateAuth.copy(
                    errorMessage = response.message
                )
                false
            }

        } catch (e: Exception) {
            uiStateAuth = uiStateAuth.copy(
                errorMessage = "password atau username salah"
            )
            false
        }
    }
}