package com.example.fashta_163.repository

import com.example.fashta_163.apiservice.ServiceApiProduct
import com.example.fashta_163.modeldata.DataProduct
import retrofit2.Response

interface RepositoryProduct {

    suspend fun getDataProduct(): List<DataProduct>

    suspend fun getProductById(id: Int): DataProduct

    suspend fun postDataProduct(
        dataProduct: DataProduct
    ): Response<Void>

    suspend fun editDataProduct(
        id: Int,
        dataProduct: DataProduct
    ): Response<Void>

    suspend fun hapusDataProduct(
        id: Int
    ): Response<Void>
}

class JaringanRepositoryProduct(
    private val serviceApiProduct: ServiceApiProduct
) : RepositoryProduct {

    override suspend fun getDataProduct(): List<DataProduct> =
        serviceApiProduct.getProduct()

    override suspend fun getProductById(id: Int): DataProduct =
        serviceApiProduct.getProductById(id)

    override suspend fun postDataProduct(
        dataProduct: DataProduct
    ): Response<Void> =
        serviceApiProduct.postProduct(dataProduct)

    override suspend fun editDataProduct(
        id: Int,
        dataProduct: DataProduct
    ): Response<Void> =
        serviceApiProduct.editProduct(id, dataProduct)

    override suspend fun hapusDataProduct(
        id: Int
    ): Response<Void> =
        serviceApiProduct.hapusProduct(id)
}