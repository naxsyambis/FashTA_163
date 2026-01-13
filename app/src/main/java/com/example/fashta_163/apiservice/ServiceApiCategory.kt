package com.example.fashta_163.apiservice

import com.example.fashta_163.modeldata.Category
import retrofit2.Response
import retrofit2.http.*

interface ServiceApiCategory {

    @GET("category/read.php")
    suspend fun getCategory(): List<Category>

    @POST("category/create.php")
    suspend fun postCategory(
        @Body category: Category
    ): Response<Void>

    @PUT("category/update.php")
    suspend fun editCategory(
        @Query("id") id: Int,
        @Body category: Category
    ): Response<Void>

    @DELETE("category/delete.php")
    suspend fun hapusCategory(
        @Query("id") id: Int
    ): Response<Void>
}