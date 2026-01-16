package com.example.fashta_163.view.stock

import android.R.id.input
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.stock.StockViewModel

@Composable
fun StockInfoSection(
    itemId: Int,
    stockViewModel: StockViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(itemId) {
        stockViewModel.loadStock(itemId)
    }

    Text(
        text = "Stok Saat Ini: ${stockViewModel.currentStock}",
        style = MaterialTheme.typography.bodyMedium
    )
}
