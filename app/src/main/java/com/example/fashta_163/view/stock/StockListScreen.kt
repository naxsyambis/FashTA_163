package com.example.fashta_163.view.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.stock.StatusUiStockList
import com.example.fashta_163.viewmodel.stock.StockListViewModel

@Composable
fun StockListScreen(
    navigateToStockMenu: (Int) -> Unit,
    viewModel: StockListViewModel = viewModel(
        factory = PenyediaViewModel.Factory
    )
) {
    LaunchedEffect(Unit) {
        viewModel.loadStockList()
    }

    var query by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.search(it)
            },
            label = { Text("Cari item / produk") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        when (val state = viewModel.statusUi) {
            is StatusUiStockList.Loading -> {
                Text("Loading...")
            }

            is StatusUiStockList.Error -> {
                Text("Gagal memuat data stok")
            }

            is StatusUiStockList.Success -> {
                LazyColumn {
                    items(state.list) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            onClick = {
                                navigateToStockMenu(item.item_id)
                            }
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(item.product_name,
                                    style = MaterialTheme.typography.titleMedium)
                                Text("Size: ${item.size} | Warna: ${item.color}")
                                Text("Stok: ${item.stock}")
                            }
                        }
                    }
                }
            }
        }
    }
}
