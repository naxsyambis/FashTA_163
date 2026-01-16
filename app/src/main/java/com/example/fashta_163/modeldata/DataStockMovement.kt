package com.example.fashta_163.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataStockMovement(
    val item_id: Int,
    val movement_type: String,
    val quantity: Int,
    val reason: String,
    val note: String? = null
)

@Serializable
data class StockResponse(
    val item_id: Int,
    val stock: Int
)

@Serializable
data class StockHistory(
    val movement_type: String,
    val quantity: Int,
    val reason: String,
    val created_at: String
)

@Serializable
data class StockItem(
    val item_id: Int,
    val product_name: String,
    val size: String,
    val color: String,
    val stock: Int
)

enum class StockReason {
    INITIAL,
    PURCHASE,
    DAMAGED,
    LOST,
    ADJUSTMENT
}
