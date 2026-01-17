package com.example.fashta_163.view.produk

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fashta_163.modeldata.DataProduct
import com.example.fashta_163.viewmodel.Produk.ProductHomeViewModel
import com.example.fashta_163.viewmodel.Produk.StatusUiProduct
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel

// --- Warna Tema Produk (Private agar aman) ---
private val ProductBgColor = Color(0xFFF9F5F0)     // Cream
private val ProductPrimary = Color(0xFF5D4037)     // Cokelat Tua
private val ProductSurface = Color(0xFFFFFFFF)     // Putih
private val ProductAccent = Color(0xFF8D6E63)      // Cokelat Muda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navigateToAdd: () -> Unit,
    navigateToEdit: (DataProduct) -> Unit,
    navigateToItemProduct: (Int) -> Unit
) {
    val viewModel: ProductHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)

    var showDeleteDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<DataProduct?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadProduct()
    }

    Scaffold(
        containerColor = ProductBgColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAdd,
                containerColor = ProductPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Produk")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // --- Header Custom ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Daftar Produk",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ProductPrimary
                    )
                )
            }

            // --- Content Based on State ---
            when (val state = viewModel.listProduct) {

                is StatusUiProduct.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = ProductPrimary)
                    }
                }

                is StatusUiProduct.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Gray)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Gagal memuat data", color = Color.Gray)
                            Button(
                                onClick = { viewModel.loadProduct() },
                                colors = ButtonDefaults.buttonColors(containerColor = ProductPrimary),
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text("Coba Lagi")
                            }
                        }
                    }
                }

                is StatusUiProduct.Success -> {
                    if (state.listProduct.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Belum ada produk", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 80.dp),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            items(state.listProduct) { product ->
                                ProductCardItem(
                                    product = product,
                                    onCardClick = { navigateToItemProduct(product.product_id) },
                                    onEditClick = { navigateToEdit(product) },
                                    onDeleteClick = {
                                        productToDelete = product
                                        showDeleteDialog = true
                                    },
                                    onReactivateClick = { viewModel.reActivateProduct(product) }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    // --- Dialog Konfirmasi ---
    if (showDeleteDialog && productToDelete != null) {
        AlertDialog(
            containerColor = ProductSurface,
            titleContentColor = ProductPrimary,
            textContentColor = Color.Gray,
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Nonaktifkan Produk", fontWeight = FontWeight.Bold) },
            text = {
                Text("Produk '${productToDelete?.product_name}' akan disembunyikan dari katalog.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.nonActiveProduct(productToDelete!!.product_id)
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Nonaktifkan")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = ProductPrimary)
                ) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun ProductCardItem(
    product: DataProduct,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onReactivateClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ProductSurface), // Card Putih
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
    ) {
        Column {
            // --- Gambar Produk ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFEEEEEE)) // Placeholder background abu
            ) {
                AsyncImage(
                    model = product.image_url,
                    contentDescription = product.product_name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Badge Status di atas gambar
                val isActive = product.is_active == 1
                Surface(
                    color = if (isActive) Color(0xCCFFFFFF) else Color(0xCCD32F2F),
                    shape = RoundedCornerShape(bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = if (isActive) "Aktif" else "Non-Aktif",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (isActive) ProductPrimary else Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            // --- Detail Info ---
            Column(modifier = Modifier.padding(16.dp)) {

                // Nama & Kategori
                Text(
                    text = product.product_name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = ProductPrimary
                )
                Text(
                    text = product.category_name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = ProductAccent
                )

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(8.dp))

                // --- Action Buttons ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (product.is_active == 1) {
                        // Tombol Delete (Icon Only agar bersih)
                        IconButton(onClick = onDeleteClick) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus",
                                tint = Color(0xFFE57373) // Merah soft
                            )
                        }
                        // Tombol Edit
                        FilledTonalButton(
                            onClick = onEditClick,
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = ProductBgColor,
                                contentColor = ProductPrimary
                            )
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Edit")
                        }
                    } else {
                        // Tombol Aktifkan Kembali
                        Button(
                            onClick = onReactivateClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)), // Hijau soft
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Aktifkan")
                        }
                    }
                }
            }
        }
    }
}