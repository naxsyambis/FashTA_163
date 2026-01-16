package com.example.fashta_163.apiservice

import com.example.fashta_163.modeldata.DataItemProduct
import com.example.fashta_163.modeldata.StockItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ServiceApiItemProduct {

    @GET("item/read.php")
    suspend fun getItemByProduct(
        @Query("product_id") productId: Int
    ): List<DataItemProduct>

    @POST("item/create.php")
    suspend fun postItemProduct(
        @Body data: DataItemProduct
    ): Response<Void>

    @PUT("item/update.php")
    suspend fun updateItemProduct(
        @Query("id") itemId: Int,
        @Body data: DataItemProduct
    ): Response<Void>

    @PUT("item/delete.php")
    suspend fun deactivateItemProduct(
        @Query("id") itemId: Int
    ): Response<Void>

    @GET("item/read_one.php")
    suspend fun getItemById(
        @Query("id") itemId: Int
    ): DataItemProduct


}