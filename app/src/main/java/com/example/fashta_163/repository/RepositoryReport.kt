package com.example.fashta_163.repository

import com.example.fashta_163.apiservice.ServiceApiReport
import com.example.fashta_163.apiservice.ServiceApiStock
import com.example.fashta_163.modeldata.StockItem
import com.example.fashta_163.modeldata.StockReport

interface RepositoryReport {

    suspend fun getStockReport(
        productId: Int?,
        itemId: Int?,
        start: String?,
        end: String?,
        type: String?
    ): List<StockReport>

    suspend fun getStockItems(): List<StockItem>
}

class JaringanRepositoryReport(
    private val serviceReport: ServiceApiReport,
    private val serviceStock: ServiceApiStock
) : RepositoryReport {

    override suspend fun getStockReport(
        productId: Int?,
        itemId: Int?,
        start: String?,
        end: String?,
        type: String?
    ): List<StockReport> {
        return serviceReport.getStockReport(
            productId = productId,
            itemId = itemId,
            startDate = start,
            endDate = end,
            type = type
        )
    }

    override suspend fun getStockItems(): List<StockItem> {
        return serviceStock.getStockList()
    }

}