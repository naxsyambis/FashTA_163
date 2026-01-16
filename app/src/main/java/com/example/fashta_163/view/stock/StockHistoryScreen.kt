package com.example.fashta_163.view.stock

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
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
fun StockHistoryScreen(
    itemId: Int,
    navigateBack: () -> Unit
) {
    val viewModel: StockViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    LaunchedEffect(itemId) {
        viewModel.loadHistory(itemId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Stok") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(viewModel.history) { item ->
                Text("${item.movement_type} - ${item.quantity} (${item.reason})")
                Text(item.created_at, style = MaterialTheme.typography.bodySmall)
                Divider()
            }
        }
    }
}
