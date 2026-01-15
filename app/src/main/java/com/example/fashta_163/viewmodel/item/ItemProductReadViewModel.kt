package com.example.fashta_163.viewmodel.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import retrofit2.HttpException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.DataItemProduct
import com.example.fashta_163.repository.RepositoryItemProduct
import com.example.fashta_163.uicontroller.route.DestinasiItemProduk
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StatusUiItemProduct {
    data class Success(val listItem: List<DataItemProduct>) : StatusUiItemProduct
    object Error : StatusUiItemProduct
    object Loading : StatusUiItemProduct
}

class ItemProductReadViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryItemProduct: RepositoryItemProduct
) : ViewModel() {


    private val productId: Int =
        checkNotNull(savedStateHandle[DestinasiItemProduk.productIdArg])

    var statusUiItemProduct by mutableStateOf<StatusUiItemProduct>(
        StatusUiItemProduct.Loading
    )
        private set

    fun loadItemProduct() {
        viewModelScope.launch {
            statusUiItemProduct = StatusUiItemProduct.Loading
            statusUiItemProduct = try {
                StatusUiItemProduct.Success(
                    repositoryItemProduct.getItemByProduct(productId)
                )
            } catch (e: Exception) {
                StatusUiItemProduct.Error
            }
        }
    }


    fun getProductId(): Int = productId

    suspend fun deactivateItem(itemId: Int) {
        repositoryItemProduct.deactivateItemProduct(itemId)
    }
}
