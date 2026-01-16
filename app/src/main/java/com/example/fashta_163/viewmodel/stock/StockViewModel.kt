package com.example.fashta_163.viewmodel.stock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.DataStockMovement
import com.example.fashta_163.modeldata.StockHistory
import com.example.fashta_163.modeldata.StockItem
import com.example.fashta_163.repository.RepositoryStock
import kotlinx.coroutines.launch

sealed interface StatusUiStockList {
    object Loading : StatusUiStockList
    object Error : StatusUiStockList
    data class Success(val list: List<StockItem>) : StatusUiStockList
}

class StockViewModel(
    private val repository: RepositoryStock
) : ViewModel() {

    var statusUi by mutableStateOf<StatusUiStockList>(
        StatusUiStockList.Loading
    )
        private set

    // 🔥 WAJIB ADA (INI YANG HILANG)
    private var fullList: List<StockItem> = emptyList()

    var currentStock by mutableStateOf(0)
        private set

    var history by mutableStateOf<List<StockHistory>>(emptyList())
        private set

    // ===== LIST & SEARCH =====
    fun loadStockList() {
        viewModelScope.launch {
            statusUi = try {
                fullList = repository.getStockList()
                StatusUiStockList.Success(fullList)
            } catch (e: Exception) {
                StatusUiStockList.Error
            }
        }
    }

    fun search(query: String) {
        val filtered = fullList.filter {
            it.product_name.contains(query, ignoreCase = true) ||
                    it.size.contains(query, ignoreCase = true) ||
                    it.color.contains(query, ignoreCase = true)
        }
        statusUi = StatusUiStockList.Success(filtered)
    }

    // ===== STOCK INFO =====
    fun loadHistory(itemId: Int) {
        viewModelScope.launch {
            history = repository.getStockHistory(itemId)
        }
    }

    fun loadStock(itemId: Int) {
        viewModelScope.launch {
            try {
                currentStock = repository.getCurrentStock(itemId)
            } catch (e: Exception) {
                currentStock = 0
            }
        }
    }

    // ===== SUBMIT STOCK =====
    fun submitStock(
        itemId: Int,
        type: String,
        qty: Int,
        reason: String,
        note: String?,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {

            // 🔒 VALIDASI BISNIS (SRS)
            if (type == "OUT" && qty > currentStock) {
                onError("Stok tidak mencukupi")
                return@launch
            }

            repository.createStockMovement(
                DataStockMovement(
                    item_id = itemId,
                    movement_type = type,
                    quantity = qty,
                    reason = reason,
                    note = note
                )
            )

            loadStock(itemId)
            onSuccess()
        }
    }
}