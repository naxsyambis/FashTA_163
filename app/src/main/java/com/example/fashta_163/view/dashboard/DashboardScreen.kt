package com.example.fashta_163.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// --- Color Palette ---
val FashionCream = Color(0xFFF9F5F0) // Background utama
val FashionSurface = Color(0xFFEBE3D9) // Warna card muda
val FashionBrown = Color(0xFF5D4037)   // Warna teks utama
val FashionAccent = Color(0xFF8D6E63)  // Warna sekunder

@Composable
fun DashboardScreen(
    onNavigateToPengaturan: () -> Unit,
    onNavigateToProduct: () -> Unit,
    onNavigateToStock: () -> Unit,
    onNavigateToReport: () -> Unit
) {
    Scaffold(
        containerColor = FashionCream
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {


            Spacer(modifier = Modifier.height(32.dp))
            HeaderSectionSimple()


            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Menu Utama",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = FashionBrown
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryItem(
                    icon = Icons.Default.ShoppingBag,
                    title = "Produk",
                    onClick = onNavigateToProduct
                )
                CategoryItem(
                    icon = Icons.Default.Inventory,
                    title = "Stok",
                    onClick = onNavigateToStock
                )
                CategoryItem(
                    icon = Icons.Default.Analytics,
                    title = "Laporan",
                    onClick = onNavigateToReport
                )
                CategoryItem(
                    icon = Icons.Default.Settings,
                    title = "Setting",
                    onClick = onNavigateToPengaturan
                )
            }
        }
    }
}



@Composable
fun HeaderSectionSimple() {
    Column {
        Text(
            text = "Lokasi Gudang",
            style = MaterialTheme.typography.bodySmall,
            color = FashionAccent
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = FashionBrown,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Yogyakarta, ID",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = FashionBrown
            )
        }
    }
}

@Composable
fun CategoryItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, FashionSurface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = FashionBrown,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = FashionBrown
        )
    }
}