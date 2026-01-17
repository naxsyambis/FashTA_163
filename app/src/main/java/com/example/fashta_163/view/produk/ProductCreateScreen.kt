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
import com.example.fashta_163.viewmodel.Produk.ProductCreateViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryReadViewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

// --- Warna Tema Create (Private agar aman) ---
private val CreateBgColor = Color(0xFFF9F5F0)      // Cream
private val CreatePrimaryColor = Color(0xFF5D4037) // Cokelat Tua
private val CreateInputBg = Color(0xFFFFFFFF)      // Putih

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCreateScreen(
    navigateBack: () -> Unit
) {
    val entryViewModel: ProductCreateViewModel = viewModel(factory = PenyediaViewModel.Factory)
    val categoryViewModel: CategoryReadViewModel = viewModel(factory = PenyediaViewModel.Factory)

    val uiState = entryViewModel.uiStateProduct
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Launcher untuk ambil gambar dari galeri
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            try {
                // Izin persistable agar gambar tetap bisa diakses nanti
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            entryViewModel.updateUiState(
                uiState.detailProduct.copy(image_url = uri.toString())
            )
        }
    }

    Scaffold(
        containerColor = CreateBgColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Tambah Produk",
                        fontWeight = FontWeight.Bold,
                        color = CreatePrimaryColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = CreatePrimaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = CreateBgColor
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // --- BAGIAN UPLOAD FOTO ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(CreateInputBg)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.detailProduct.image_url.isNotEmpty()) {
                    // Tampilkan Gambar yang Dipilih
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = uiState.detailProduct.image_url,
                            contentDescription = "Preview Gambar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Overlay Icon Edit (Pencil)
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .size(36.dp)
                                .align(Alignment.BottomEnd)
                                .background(CreatePrimaryColor.copy(alpha = 0.8f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Ganti Foto",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                } else {
                    // Tampilan Kosong (Prompt Upload)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ketuk untuk upload foto",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // --- INPUT NAMA PRODUK ---
            OutlinedTextField(
                value = uiState.detailProduct.product_name,
                onValueChange = {
                    entryViewModel.updateUiState(
                        uiState.detailProduct.copy(product_name = it)
                    )
                },
                label = { Text("Nama Produk") },
                placeholder = { Text("Masukkan nama produk...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CreatePrimaryColor,
                    focusedLabelColor = CreatePrimaryColor,
                    cursorColor = CreatePrimaryColor,
                    focusedContainerColor = CreateInputBg,
                    unfocusedContainerColor = CreateInputBg
                ),
                singleLine = true
            )

            // --- DROPDOWN KATEGORI ---
            // Dibungkus Card agar style konsisten dengan Input Field
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CreateInputBg),
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
                            entryViewModel.updateUiState(
                                uiState.detailProduct.copy(category_id = id)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Dorong tombol ke bawah

            // --- TOMBOL SIMPAN ---
            Button(
                enabled = uiState.isEntryValid,
                onClick = {
                    coroutineScope.launch {
                        entryViewModel.addProduct()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CreatePrimaryColor,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(
                    text = "Simpan Produk",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}