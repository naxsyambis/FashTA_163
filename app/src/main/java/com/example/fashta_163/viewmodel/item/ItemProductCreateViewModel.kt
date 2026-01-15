package com.example.fashta_163.viewmodel.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.fashta_163.modeldata.DataItemProduct
import com.example.fashta_163.modeldata.DetailItemProduct
import com.example.fashta_163.modeldata.UIStateItemProduct
import com.example.fashta_163.modeldata.toDataItemProduct
import com.example.fashta_163.repository.RepositoryItemProduct
import com.example.fashta_163.uicontroller.route.DestinasiItemProduk

class ItemProductCreateViewModel(
    private val repositoryItemProduct: RepositoryItemProduct,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int =
        checkNotNull(savedStateHandle["productId"])

    var uiStateItemProduct by mutableStateOf(UIStateItemProduct())
        private set

    fun updateUiState(detailItemProduct: DetailItemProduct) {
        uiStateItemProduct = UIStateItemProduct(
            detailItemProduct = detailItemProduct,
            isEntryValid = detailItemProduct.size.isNotBlank()
                    && detailItemProduct.color.isNotBlank()
                    && detailItemProduct.price.toDoubleOrNull() != null
        )
    }

    suspend fun addItemProduct() {
        if (!uiStateItemProduct.isEntryValid) return

        val data = uiStateItemProduct.detailItemProduct

        val itemProduct = DataItemProduct(
            product_id = productId,
            size = data.size,
            color = data.color,
            price = data.price.toDouble(),
            is_active = 1
        )

        println("DEBUG productId = $productId")

        repositoryItemProduct.postItemProduct(itemProduct)
    }
}