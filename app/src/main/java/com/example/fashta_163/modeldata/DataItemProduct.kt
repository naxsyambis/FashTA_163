package com.example.fashta_163.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataItemProduct(
    val item_id: Int = 0,
    val product_id: Int = 0,
    val size: String = "",
    val color: String = "",
    val price: Double = 0.0,
    val is_active: Int = 1
)

data class DetailItemProduct(
    val item_id: Int = 0,
    val product_id: Int = 0,
    val size: String = "",
    val color: String = "",
    val price: String = ""
)

data class UIStateItemProduct(
    val detailItemProduct: DetailItemProduct = DetailItemProduct(),
    val isEntryValid: Boolean = false
)


fun DetailItemProduct.toDataItemProduct(): DataItemProduct =
    DataItemProduct(
        item_id = item_id,
        product_id = product_id,
        size = size,
        color = color,
        price = price.toDoubleOrNull() ?: 0.0
    )

fun DataItemProduct.toDetailItemProduct(): DetailItemProduct =
    DetailItemProduct(
        item_id = item_id,
        product_id = product_id,
        size = size,
        color = color,
        price = price.toString()
    )
