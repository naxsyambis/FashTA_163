package com.example.fashta_163.view.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.stock.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockMenuScreen(
    itemId: Int,
    navigateToStockIn: (Int) -> Unit,
    navigateToStockOut: (Int) -> Unit,
    navigateBack: () -> Unit
) {

    val viewModel: StockViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    // 🔥 Ambil stok saat screen dibuka
    LaunchedEffect(itemId) {
        viewModel.loadStock(itemId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kelola Stok") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // ===== INFO STOK =====
            Text(
                text = "Stok Saat Ini: ${viewModel.currentStock}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))

            // ===== STOCK IN (SELALU AKTIF) =====
            Button(
                onClick = { navigateToStockIn(itemId) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Stock IN")
            }

            Spacer(Modifier.height(12.dp))

            // ===== STOCK OUT (DISABLE JIKA STOK = 0) =====
            Button(
                onClick = { navigateToStockOut(itemId) },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.currentStock > 0   // 🔥 INI INTINYA
            ) {
                Text("Stock OUT")
            }

            // ===== PESAN JIKA STOK KOSONG =====
            if (viewModel.currentStock == 0) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Stok kosong, tidak bisa melakukan Stock OUT",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}