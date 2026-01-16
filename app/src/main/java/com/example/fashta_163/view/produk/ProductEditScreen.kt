package com.example.fashta_163.view.produk

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fashta_163.viewmodel.Produk.ProductEditViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryReadViewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditScreen(
    navigateBack: () -> Unit
) {
    val editViewModel: ProductEditViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val categoryViewModel: CategoryReadViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val uiState = editViewModel.uiStateProduct
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            editViewModel.updateUiState(
                uiState.detailProduct.copy(
                    image_url = uri.toString()
                )
            )

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Produk") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("Foto Produk")
            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.detailProduct.image_url.isNotEmpty()) {
                AsyncImage(
                    model = uiState.detailProduct.image_url,
                    contentDescription = "Foto Produk",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {
                            imagePickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                Button(
                    onClick = {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pilih Foto Produk")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = uiState.detailProduct.product_name,
                onValueChange = {
                    editViewModel.updateUiState(
                        uiState.detailProduct.copy(
                            product_name = it
                        )
                    )
                },
                label = { Text("Nama Produk*") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropdown(
                categoryViewModel = categoryViewModel,
                selectedCategoryId = uiState.detailProduct.category_id,
                onCategorySelected = { id ->
                    editViewModel.updateUiState(
                        uiState.detailProduct.copy(
                            category_id = id
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                enabled = uiState.isEntryValid,
                onClick = {
                    coroutineScope.launch {
                        editViewModel.updateProduct()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update")
            }
        }
    }
}