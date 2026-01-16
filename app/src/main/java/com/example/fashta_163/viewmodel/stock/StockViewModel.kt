package com.example.fashta_163.viewmodel.stock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.DataStockMovement
import com.example.fashta_163.modeldata.StockHistory
import com.example.fashta_163.repository.RepositoryStock
import kotlinx.coroutines.launch

class StockViewModel(
    private val repository: RepositoryStock
) : ViewModel() {

    var currentStock by mutableStateOf(0)
        private set

    var history by mutableStateOf<List<StockHistory>>(emptyList())
        private set



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

    fun submitStock(
        itemId: Int,
        type: String,
        qty: Int,
        reason: String,
        note: String?,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            repository.createStockMovement(
                DataStockMovement(
                    item_id = itemId,
                    movement_type = type,
                    quantity = qty,
                    reason = reason,
                    note = note
                )
            )
            onSuccess()
        }
    }
}
