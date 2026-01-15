package com.example.fashta_163.viewmodel.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.DataItemProduct
import com.example.fashta_163.modeldata.DetailItemProduct
import com.example.fashta_163.modeldata.UIStateItemProduct
import com.example.fashta_163.modeldata.toDataItemProduct
import com.example.fashta_163.modeldata.toDetailItemProduct
import com.example.fashta_163.repository.RepositoryItemProduct
import com.example.fashta_163.uicontroller.route.DestinasiItemProdukEdit
import kotlinx.coroutines.launch

class ItemProductEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryItemProduct: RepositoryItemProduct
) : ViewModel() {

    private val itemId: Int =
        checkNotNull(savedStateHandle[DestinasiItemProdukEdit.itemIdArg])

    // 🔥 INI KUNCI UTAMA
    var uiStateItemProduct by mutableStateOf(UIStateItemProduct())
        private set

    init {
        loadItem()
    }

    private fun loadItem() {
        viewModelScope.launch {
            val item = repositoryItemProduct.getItemById(itemId)
            uiStateItemProduct = UIStateItemProduct(
                detailItemProduct = item.toDetailItemProduct(),
                isEntryValid = true
            )
        }
    }

    // 🔥 WAJIB PAKAI copy(), JANGAN BUAT STATE BARU SEMBARANG
    fun updateUiState(detailItemProduct: DetailItemProduct) {
        uiStateItemProduct = uiStateItemProduct.copy(
            detailItemProduct = detailItemProduct
        )
    }

    suspend fun updateItemProduct() {
        val detail = uiStateItemProduct.detailItemProduct
        repositoryItemProduct.updateItemProduct(
            itemId,
            detail.toDataItemProduct()
        )
    }
}
