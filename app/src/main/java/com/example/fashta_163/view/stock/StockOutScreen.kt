package com.example.fashta_163.view.stock

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.stock.StockViewModel

@Composable
fun StockOutScreen(
    itemId: Int,
    viewModel: StockViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit
) {
    StockForm(
        itemId = itemId,
        type = "IN",
        viewModel = viewModel,
        onSuccess = {
            viewModel.loadStock(itemId)
            navigateBack()
        }
    )
}
