package com.example.fashta_163.apiservice

import com.example.fashta_163.modeldata.DataProduct
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ServiceApiProduct {

    @GET("product/read.php")
    suspend fun getProduct(): List<DataProduct>

    @GET("product/read_one.php")
    suspend fun getProductById(
        @Query("id") id: Int
    ): DataProduct

    @POST("product/create.php")
    suspend fun postProduct(
        @Body data: DataProduct
    ): Response<Void>

    @PUT("product/update.php")
    suspend fun editProduct(
        @Query("id") id: Int,
        @Body data: DataProduct
    ): Response<Void>

    @DELETE("product/delete.php")
    suspend fun hapusProduct(
        @Query("id") id: Int
    ): Response<Void>
}