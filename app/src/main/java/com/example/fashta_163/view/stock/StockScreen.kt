package com.example.fashta_163.view.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.stock.StockViewModel

// --- Warna Tema Lokal (Private) ---
private val StockPrimaryColor = Color(0xFF5D4037) // Cokelat Tua
private val StockCardBg = Color(0xFFFFFFFF)       // Putih

@Composable
fun StockInfoSection(
    itemId: Int,
    stockViewModel: StockViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(itemId) {
        stockViewModel.loadStock(itemId)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = StockCardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Container
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(StockPrimaryColor.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Inventory2,
                    contentDescription = "Ikon Stok",
                    tint = StockPrimaryColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Info
            Column {
                Text(
                    text = "Stok Saat Ini",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "${stockViewModel.currentStock} Pcs",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = StockPrimaryColor
                )
            }
        }
    }
}