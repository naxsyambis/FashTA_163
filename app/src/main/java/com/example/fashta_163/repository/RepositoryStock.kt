package com.example.fashta_163.repository

import com.example.fashta_163.apiservice.ServiceApiStock
import com.example.fashta_163.modeldata.ApiMessage
import com.example.fashta_163.modeldata.DataStockMovement
import com.example.fashta_163.modeldata.StockHistory
import com.example.fashta_163.modeldata.StockItem
import com.example.fashta_163.modeldata.StockItemDetail
import retrofit2.Response

interface RepositoryStock {
    suspend fun createStockMovement(
        data: DataStockMovement
    ): Response<ApiMessage>

    suspend fun getCurrentStock(itemId: Int): Int

    suspend fun getStockHistory(itemId: Int): List<StockHistory>

    suspend fun getStockList(): List<StockItem>

    suspend fun getItemDetail(itemId: Int): StockItemDetail

}

class JaringanRepositoryStock(
    private val service: ServiceApiStock
) : RepositoryStock {

    override suspend fun createStockMovement(
        data: DataStockMovement
    ): Response<ApiMessage> =
        service.createStockMovement(data)

    override suspend fun getCurrentStock(itemId: Int): Int {
        return service.getCurrentStock(itemId).stock
    }

    override suspend fun getStockHistory(itemId: Int): List<StockHistory> {
        return service.getStockHistory(itemId)
    }

    override suspend fun getStockList(): List<StockItem> {
        return service.getStockList()
    }

    override suspend fun getItemDetail(itemId: Int): StockItemDetail {
        return service.getItemDetail(itemId)
    }


}