package com.example.fashta_163.view.produk

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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