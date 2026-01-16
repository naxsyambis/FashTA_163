package com.example.fashta_163.apiservice

import com.example.fashta_163.modeldata.StockReport
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApiReport {

    @GET("reports/report.php")
    suspend fun getStockReport(
        @Query("product_id") productId: Int? = null,
        @Query("item_id") itemId: Int? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("type") type: String? = null
    ): List<StockReport>
}
