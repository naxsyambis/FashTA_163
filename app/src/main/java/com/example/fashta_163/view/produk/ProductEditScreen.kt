package com.example.fashta_163.view.produk

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fashta_163.viewmodel.Produk.ProductEditViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryReadViewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

// --- Warna Tema Edit (Private agar aman) ---
private val EditBgColor = Color(0xFFF9F5F0)      // Cream
private val EditPrimaryColor = Color(0xFF5D4037) // Cokelat Tua
private val EditInputBg = Color(0xFFFFFFFF)      // Putih

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditScreen(
    navigateBack: () -> Unit
) {
    val editViewModel: ProductEditViewModel = viewModel(factory = PenyediaViewModel.Factory)
    val categoryViewModel: CategoryReadViewModel = viewModel(factory = PenyediaViewModel.Factory)

    val uiState = editViewModel.uiStateProduct
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Launcher Galeri
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            // Izin akses URI persisten (Penting agar gambar tidak hilang saat restart)
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            editViewModel.updateUiState(
                uiState.detailProduct.copy(image_url = uri.toString())
            )
        }
    }

    Scaffold(
        containerColor = EditBgColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Edit Produk",
                        fontWeight = FontWeight.Bold,
                        color = EditPrimaryColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = EditPrimaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = EditBgColor
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp) // Jarak antar elemen rapi
        ) {

            // --- BAGIAN FOTO PRODUK ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(EditInputBg)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.detailProduct.image_url.isNotEmpty()) {
                    // Jika ada gambar
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = uiState.detailProduct.image_url,
                            contentDescription = "Foto Produk",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Overlay Icon Edit Kecil
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .size(36.dp)
                                .align(Alignment.BottomEnd)
                                .background(EditPrimaryColor.copy(alpha = 0.8f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Ubah Foto",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                } else {
                    // Jika belum ada gambar (Placeholder)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Ketuk untuk tambah foto", color = Color.Gray)
                    }
                }
            }

            // --- FORM INPUT ---

            // Input Nama Produk
            OutlinedTextField(
                value = uiState.detailProduct.product_name,
                onValueChange = {
                    editViewModel.updateUiState(uiState.detailProduct.copy(product_name = it))
                },
                label = { Text("Nama Produk") },
                placeholder = { Text("Contoh: Kemeja Flanel") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EditPrimaryColor,
                    focusedLabelColor = EditPrimaryColor,
                    cursorColor = EditPrimaryColor,
                    focusedContainerColor = EditInputBg,
                    unfocusedContainerColor = EditInputBg
                ),
                singleLine = true
            )

            // Input Kategori (Dropdown)
            // Pastikan Anda sudah memiliki Composable CategoryDropdown yang valid
            // Saya bungkus dalam Card agar style-nya konsisten jika dropdown-nya transparan
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = EditInputBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            ) {
                Box(modifier = Modifier.padding(4.dp)) {
                    CategoryDropdown(
                        categoryViewModel = categoryViewModel,
                        selectedCategoryId = uiState.detailProduct.category_id,
                        onCategorySelected = { id ->
                            editViewModel.updateUiState(
                                uiState.detailProduct.copy(category_id = id)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Dorong tombol ke bawah

            // --- TOMBOL UPDATE ---
            Button(
                enabled = uiState.isEntryValid,
                onClick = {
                    coroutineScope.launch {
                        editViewModel.updateProduct()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EditPrimaryColor,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(
                    text = "Simpan Perubahan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}