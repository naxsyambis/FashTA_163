package com.example.fashta_163.viewmodel.pengaturan

import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.Category
import com.example.fashta_163.repository.RepositoryCategory
import retrofit2.Response

class CategoryCreateViewModel(
    private val repositoryCategory: RepositoryCategory
) : ViewModel() {

    suspend fun createCategory(category: Category): Response<Void> {
        return repositoryCategory.postCategory(category)
    }
}