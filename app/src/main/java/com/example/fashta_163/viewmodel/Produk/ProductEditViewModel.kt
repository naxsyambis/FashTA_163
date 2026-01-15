package com.example.fashta_163.viewmodel.Produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.DataProduct
import com.example.fashta_163.modeldata.DetailProduct
import com.example.fashta_163.modeldata.UIStateProduct
import com.example.fashta_163.modeldata.toDataProduct
import com.example.fashta_163.repository.RepositoryProduct
import kotlinx.coroutines.launch

class ProductEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryProduct: RepositoryProduct
) : ViewModel() {

    private val productId: Int =
        checkNotNull(savedStateHandle["productId"])

    var uiStateProduct by mutableStateOf(UIStateProduct())
        private set

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            val product = repositoryProduct.getProductById(productId)
            uiStateProduct = UIStateProduct(
                detailProduct = DetailProduct(
                    product_id = product.product_id,
                    product_name = product.product_name,
                    category_id = product.category_id,
                    image_url = product.image_url ?: ""
                ),
                isEntryValid = true
            )
        }
    }

    fun updateUiState(detailProduct: DetailProduct) {
        uiStateProduct = UIStateProduct(
            detailProduct = detailProduct,
            isEntryValid = validasiInput(detailProduct)
        )
    }

    private fun validasiInput(detail: DetailProduct): Boolean {
        return detail.product_name.isNotBlank() &&
                detail.category_id != 0
    }

    suspend fun updateProduct() {
        if (!uiStateProduct.isEntryValid) return

        repositoryProduct.editDataProduct(
            productId, //
            uiStateProduct.detailProduct.toDataProduct()
        )
    }
}