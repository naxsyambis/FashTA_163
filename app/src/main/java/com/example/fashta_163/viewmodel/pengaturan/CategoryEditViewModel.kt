package com.example.fashta_163.viewmodel.pengaturan

import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.Category
import com.example.fashta_163.repository.RepositoryCategory
import retrofit2.Response

class CategoryEditViewModel(
    private val repositoryCategory: RepositoryCategory
) : ViewModel() {

    suspend fun editCategory(
        id: Int,
        category: Category
    ): Response<Void> {
        return repositoryCategory.editCategory(id, category)
    }
}