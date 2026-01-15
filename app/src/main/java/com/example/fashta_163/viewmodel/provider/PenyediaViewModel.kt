package com.example.fashta_163.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fashta_163.repository.AplikasiFash
import com.example.fashta_163.viewmodel.LoginViewModel
import com.example.fashta_163.viewmodel.Produk.ProductCreateViewModel
import com.example.fashta_163.viewmodel.Produk.ProductEditViewModel
import com.example.fashta_163.viewmodel.Produk.ProductHomeViewModel
import com.example.fashta_163.viewmodel.RegisterViewModel
import com.example.fashta_163.viewmodel.item.ItemProductCreateViewModel
import com.example.fashta_163.viewmodel.item.ItemProductEditViewModel
import com.example.fashta_163.viewmodel.item.ItemProductReadViewModel
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

        initializer {
            ProductHomeViewModel(
                aplikasiFash().containerApp.repositoryProduct
            )
        }
        initializer {
            ProductCreateViewModel(
                aplikasiFash().containerApp.repositoryProduct
            )
        }
        initializer {
            ProductEditViewModel(
                createSavedStateHandle(),
                aplikasiFash().containerApp.repositoryProduct
            )
        }

        initializer {
            ItemProductReadViewModel(
                createSavedStateHandle(),
                aplikasiFash().containerApp.repositoryItemProduct
            )
        }

        initializer {
            ItemProductCreateViewModel(
                aplikasiFash().containerApp.repositoryItemProduct
            )
        }

        initializer {
            ItemProductEditViewModel(
                createSavedStateHandle(),
                aplikasiFash().containerApp.repositoryItemProduct
            )
        }

    }
}
