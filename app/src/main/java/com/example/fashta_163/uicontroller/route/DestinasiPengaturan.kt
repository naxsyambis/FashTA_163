package com.example.fashta_163.uicontroller.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.R
import com.example.fashta_163.modeldata.Category
import com.example.fashta_163.viewmodel.pengaturan.CategoryCreateViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryDeleteViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryEditViewModel
import com.example.fashta_163.viewmodel.pengaturan.CategoryReadViewModel
import com.example.fashta_163.viewmodel.pengaturan.StatusUiCategory
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


object DestinasiKelolaKategori : DestinasiNavigasi {
    override val route = "kelola_kategori"
    override val titleRes = R.string.kelola_kategori
}

object DestinasiPengaturan : DestinasiNavigasi {
    override val route = "pengaturan"
    override val titleRes = R.string.pengaturan
}

@Composable
fun KelolaKategoriScreen() {

    val readViewModel: CategoryReadViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val createViewModel: CategoryCreateViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val editViewModel: CategoryEditViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val deleteViewModel: CategoryDeleteViewModel =
        viewModel(factory = PenyediaViewModel.Factory)

    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }
    var editCategory by remember { mutableStateOf<Category?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editCategory = null
                    showDialog = true
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {

            when (val state = readViewModel.statusUiCategory) {

                is StatusUiCategory.Loading -> {
                    Text("Loading...")
                }

                is StatusUiCategory.Error -> {
                    Text("Gagal memuat kategori")
                }

                is StatusUiCategory.Success -> {
                    LazyColumn {
                        items(state.listCategory) { category ->
                            ItemCategory(
                                category = category,
                                onEdit = {
                                    editCategory = category
                                    showDialog = true
                                },
                                onDelete = {
                                    coroutineScope.launch {
                                        deleteViewModel.deleteCategory(
                                            category.category_id
                                        )
                                        readViewModel.loadCategory()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        DialogKategori(
            category = editCategory,
            onDismiss = { showDialog = false },
            onSubmit = { name ->
                coroutineScope.launch {
                    if (editCategory == null) {
                        createViewModel.createCategory(
                            Category(category_name = name)
                        )
                    } else {
                        editViewModel.editCategory(
                            editCategory!!.category_id,
                            Category(category_name = name)
                        )
                    }
                    readViewModel.loadCategory()
                    showDialog = false
                }
            }
        )
    }
}

@Composable
fun ItemCategory(
    category: Category,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category.category_name)

            Row {
                TextButton(onClick = onEdit) {
                    Text("Edit")
                }
                TextButton(onClick = onDelete) {
                    Text("Hapus")
                }
            }
        }
    }
}

@Composable
fun DialogKategori(
    category: Category?,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var name by remember {
        mutableStateOf(category?.category_name ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onSubmit(name) }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        },
        title = {
            Text(
                if (category == null)
                    "Tambah Kategori"
                else
                    "Edit Kategori"
            )
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Kategori") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}
