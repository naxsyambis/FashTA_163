package com.example.fashta_163.viewmodel.stock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.StockItem
import com.example.fashta_163.repository.RepositoryStock
import kotlinx.coroutines.launch

class StockListViewModel(
    private val repository: RepositoryStock
) : ViewModel() {

    var statusUi by mutableStateOf<StatusUiStockList>(
        StatusUiStockList.Loading
    )
        private set

    private var fullList: List<StockItem> = emptyList()

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
}
