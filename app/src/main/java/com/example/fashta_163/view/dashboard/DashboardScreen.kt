package com.example.fashta_163.view.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToPengaturan: () -> Unit,
    onNavigateToProduct: () -> Unit,
    onNavigateToStock: () -> Unit
) {
    Button(onClick = onNavigateToStock) {
        Text("Kelola Stok")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // ===== CARD MENU PENGATURAN =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToPengaturan() }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Pengaturan",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Kelola kategori dan logout",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // ===== MENU PRODUK =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToProduct() } //
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Produk",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Kelola produk dan item produk",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToStock() }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "Stok",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Kelola stok masuk dan keluar",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
