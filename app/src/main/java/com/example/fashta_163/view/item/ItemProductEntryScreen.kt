package com.example.fashta_163.view.item

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.item.ItemProductCreateViewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemProductEntryScreen(
    productId: Int,
    navigateBack: () -> Unit
) {
    val viewModel: ItemProductCreateViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val uiState = viewModel.uiStateItemProduct
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tambah Item Produk") })
        }
    ) { padding ->

        ItemProductForm(
            modifier = Modifier.padding(padding),
            detail = uiState.detailItemProduct,
            onValueChange = viewModel::updateUiState,
            isButtonEnabled = uiState.isEntryValid,
            buttonText = "Simpan",
            onSubmit = {
                coroutineScope.launch {
                    viewModel.addItemProduct()
                    navigateBack()
                }
            }
        )
    }
}
