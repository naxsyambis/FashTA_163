package com.example.fashta_163.viewmodel.pengaturan

import androidx.lifecycle.ViewModel
import com.example.fashta_163.repository.RepositoryCategory
import retrofit2.Response

class CategoryDeleteViewModel(
    private val repositoryCategory: RepositoryCategory
) : ViewModel() {

    suspend fun deleteCategory(id: Int): Response<Void> {
        return repositoryCategory.hapusCategory(id)
    }
}