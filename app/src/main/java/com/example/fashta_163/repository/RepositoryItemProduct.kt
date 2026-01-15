package com.example.fashta_163.repository

import android.R.attr.id
import com.example.fashta_163.apiservice.ServiceApiItemProduct
import com.example.fashta_163.modeldata.DataItemProduct
import retrofit2.Response

interface RepositoryItemProduct {

    suspend fun getItemByProduct(
        productId: Int
    ): List<DataItemProduct>

    suspend fun postItemProduct(
        itemProduct: DataItemProduct
    ): Response<Void>

    suspend fun updateItemProduct(
        itemId: Int,
        itemProduct: DataItemProduct
    ): Response<Void>

    suspend fun deactivateItemProduct(
        itemId: Int
    ): Response<Void>

    suspend fun getItemById(itemId: Int): DataItemProduct


}

class JaringanRepositoryItemProduct(
    private val serviceApiItemProduct: ServiceApiItemProduct
) : RepositoryItemProduct {

    override suspend fun getItemByProduct(
        productId: Int
    ): List<DataItemProduct> =
        serviceApiItemProduct.getItemByProduct(productId)

    override suspend fun postItemProduct(
        itemProduct: DataItemProduct
    ): Response<Void> =
        serviceApiItemProduct.postItemProduct(itemProduct)

    override suspend fun updateItemProduct(
        ItemId: Int,
        itemProduct: DataItemProduct
    ) {
        serviceApiItemProduct.updateItemProduct(itemId = Int, itemProduct)
    }
    override suspend fun deactivateItemProduct(
        itemId: Int
    ): Response<Void> =
        serviceApiItemProduct.deactivateItemProduct(itemId)

    override suspend fun getItemById(itemId: Int): DataItemProduct =
        serviceApiItemProduct.getItemById(itemId)

}