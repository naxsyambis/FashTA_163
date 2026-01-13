package com.example.fashta_163.repository
import com.example.fashta_163.apiservice.ServiceApiCategory
import com.example.fashta_163.modeldata.Category
import retrofit2.Response

interface RepositoryCategory {

    suspend fun getCategory(): List<Category>

    suspend fun postCategory(
        category: Category
    ): Response<Void>

    suspend fun editCategory(
        id: Int,
        category: Category
    ): Response<Void>

    suspend fun hapusCategory(
        id: Int
    ): Response<Void>
}

class JaringanRepositoryCategory(
    private val serviceApiCategory: ServiceApiCategory
) : RepositoryCategory {

    override suspend fun getCategory(): List<Category> =
        serviceApiCategory.getCategory()

    override suspend fun postCategory(
        category: Category
    ): Response<Void> =
        serviceApiCategory.postCategory(category)

    override suspend fun editCategory(
        id: Int,
        category: Category
    ): Response<Void> =
        serviceApiCategory.editCategory(id, category)

    override suspend fun hapusCategory(
        id: Int
    ): Response<Void> =
        serviceApiCategory.hapusCategory(id)
}