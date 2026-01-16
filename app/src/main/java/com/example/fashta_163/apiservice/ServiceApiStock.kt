package com.example.fashta_163.apiservice

import com.example.fashta_163.modeldata.ApiMessage
import com.example.fashta_163.modeldata.DataStockMovement
import com.example.fashta_163.modeldata.StockHistory
import com.example.fashta_163.modeldata.StockItem
import com.example.fashta_163.modeldata.StockItemDetail
import com.example.fashta_163.modeldata.StockResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApiStock {

    @POST("stock/create.php")
    suspend fun createStockMovement(
        @Body data: DataStockMovement
    ): Response<ApiMessage>

    @GET("stock/read_history.php")
    suspend fun getStockHistory(
        @Query("item_id") itemId: Int
    ): List<StockHistory>

    @GET("stock/read_current.php")
    suspend fun getCurrentStock(
        @Query("item_id") itemId: Int
    ): StockResponse

    @GET("stock/read_list.php")
    suspend fun getStockList(): List<StockItem>

    @GET("stock/read_item_detail.php")
    suspend fun getItemDetail(
        @Query("item_id") itemId: Int
    ): StockItemDetail


}
