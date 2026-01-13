package com.example.fashta_163.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fashta_163.repository.AplikasiFash
import com.example.fashta_163.viewmodel.LoginViewModel
import com.example.fashta_163.viewmodel.RegisterViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryCreateViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryDeleteViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryEditViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryReadViewModel

fun CreationExtras.aplikasiFash(): AplikasiFash =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
            as AplikasiFash

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                aplikasiFash().containerApp.repositoryAuth
            )
        }
        initializer {
            RegisterViewModel(
                aplikasiFash().containerApp.repositoryAuth
            )
        }

        // ===== CATEGORY / PENGATURAN =====
        initializer {
            CategoryReadViewModel(
                aplikasiFash().containerApp.repositoryCategory
            )
        }

        initializer {
            CategoryCreateViewModel(
                aplikasiFash().containerApp.repositoryCategory
            )
        }

        initializer {
            CategoryEditViewModel(
                aplikasiFash().containerApp.repositoryCategory
            )
        }

        initializer {
            CategoryDeleteViewModel(
                aplikasiFash().containerApp.repositoryCategory
            )
        }
    }
}
