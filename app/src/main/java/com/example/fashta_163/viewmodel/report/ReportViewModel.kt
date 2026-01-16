package com.example.fashta_163.viewmodel.report

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.DataItemProduct
import com.example.fashta_163.modeldata.DataProduct
import com.example.fashta_163.modeldata.StockItem
import com.example.fashta_163.modeldata.StockReport
import com.example.fashta_163.repository.RepositoryItemProduct
import com.example.fashta_163.repository.RepositoryProduct
import com.example.fashta_163.repository.RepositoryReport
import com.example.fashta_163.repository.RepositoryStock
import kotlinx.coroutines.launch
import java.time.LocalDate

class ReportViewModel(
    private val repositoryProduct: RepositoryProduct,
    private val repositoryItemProduct: RepositoryItemProduct,
    private val repositoryReport: RepositoryReport
) : ViewModel() {

    /* ===== DATA MASTER ===== */

    var products by mutableStateOf<List<DataProduct>>(emptyList())
        private set

    var items by mutableStateOf<List<DataItemProduct>>(emptyList())
        private set

    var reports by mutableStateOf<List<StockReport>>(emptyList())
        private set

    /* ===== FILTER STATE ===== */

    var selectedProduct by mutableStateOf<DataProduct?>(null)
        private set

    var selectedItem by mutableStateOf<DataItemProduct?>(null)
        private set

    var selectedType by mutableStateOf<String?>(null)
        private set

    var startDate by mutableStateOf<LocalDate?>(null)
        private set

    var endDate by mutableStateOf<LocalDate?>(null)
        private set

    /* ===== LOAD DATA ===== */

    fun loadProducts() {
        viewModelScope.launch {
            products = repositoryProduct.getDataProduct()
        }
    }

    fun onProductSelected(product: DataProduct?) {
        selectedProduct = product
        selectedItem = null
        items = emptyList()

        product?.let {
            loadItemsByProduct(it.product_id)
        }
    }

    private fun loadItemsByProduct(productId: Int) {
        viewModelScope.launch {
            try {
                items = repositoryItemProduct.getItemByProduct(productId)
            } catch (e: Exception) {
                e.printStackTrace()
                items = emptyList()
            }
        }
    }

    fun onItemSelected(item: DataItemProduct?) {
        selectedItem = item
    }

    fun onTypeSelected(type: String?) {
        selectedType = type
    }

    fun onStartDateSelected(date: LocalDate) {
        startDate = date
    }

    fun onEndDateSelected(date: LocalDate) {
        endDate = date
    }

    fun loadReport() {
        viewModelScope.launch {
            try {
                reports = repositoryReport.getStockReport(
                    productId = selectedProduct?.product_id,
                    itemId = selectedItem?.item_id,
                    start = startDate?.toString(),
                    end = endDate?.toString(),
                    type = selectedType
                )
            } catch (e: Exception) {
                e.printStackTrace()
                reports = emptyList()
            }
        }
    }
}