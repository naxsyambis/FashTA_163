package com.example.fashta_163.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.modeldata.DataItemProduct
import com.example.fashta_163.uicontroller.route.DestinasiStockMenu
import com.example.fashta_163.viewmodel.item.ItemProductReadViewModel
import com.example.fashta_163.viewmodel.item.StatusUiItemProduct
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemProductScreen(
    navigateToAdd: (Int) -> Unit,
    navigateToEdit: (Int) -> Unit,
    navigateToStock: (Int) -> Unit
) {
    val viewModel: ItemProductReadViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    LaunchedEffect(Unit) {
        viewModel.loadItemProduct()
    }

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    var showDeactivateDialog by remember { mutableStateOf(false) }
    var selectedItemId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Item Produk") }
            )
        },

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToAdd(viewModel.getProductId())
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {
            when (val state = viewModel.statusUiItemProduct) {
                is StatusUiItemProduct.Loading -> {
                    Text("Loading...")
                }

                is StatusUiItemProduct.Error -> {
                    Text("Gagal memuat item produk")
                }

                is StatusUiItemProduct.Success -> {
                    if (state.listItem.isEmpty()) {
                        Text("Belum ada item produk")
                    } else {
                        LazyColumn {
                            items(state.listItem) { item ->
                                ItemProductCard(
                                    itemProduct = item,
                                    onEdit = {
                                        navigateToEdit(item.item_id)
                                    },
                                    onDeactivate = {
                                        selectedItemId = item.item_id
                                        showDeactivateDialog = true
                                    },
                                    onStock = {
                                        navigateToStock(item.item_id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeactivateDialog && selectedItemId != null) {
        ItemProductDeactivateDialog(
            onConfirm = {
                showDeactivateDialog = false
                coroutineScope.launch {
                    viewModel.deactivateItem(selectedItemId!!)
                    viewModel.loadItemProduct()
                    snackbarHostState.showSnackbar(
                        message = "Item produk berhasil dinonaktifkan"
                    )
                    selectedItemId = null
                }
            },
            onDismiss = {
                showDeactivateDialog = false
                selectedItemId = null
            }
        )
    }

}




@Composable
fun ItemProductCard(
    itemProduct: DataItemProduct,
    onEdit: () -> Unit,
    onDeactivate: () -> Unit,
    onStock: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (itemProduct.is_active == 1)
                MaterialTheme.colorScheme.surface
            else
                Color(0xFFF5F5F5)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Size: ${itemProduct.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Warna: ${itemProduct.color}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Harga: Rp ${itemProduct.price}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = if (itemProduct.is_active == 1)
                    "Status: Aktif"
                else
                    "Status: Nonaktif",
                color = if (itemProduct.is_active == 1)
                    Color(0xFF2E7D32)
                else
                    Color.Red,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                IconButton(
                    onClick = onStock,
                    enabled = itemProduct.is_active == 1
                ) {
                    Icon(
                        imageVector = Icons.Default.Inventory,
                        contentDescription = "Kelola Stok"
                    )
                }

                IconButton(
                    onClick = onEdit,
                    enabled = itemProduct.is_active == 1
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Item",
                        tint = if (itemProduct.is_active == 1)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray
                    )
                }

                if (itemProduct.is_active == 1) {
                    IconButton(onClick = onDeactivate) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Nonaktifkan Item",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
