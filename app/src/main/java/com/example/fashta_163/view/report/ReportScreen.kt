package com.example.fashta_163.view.report

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import com.example.fashta_163.viewmodel.report.ReportViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    navigateBack: () -> Unit,
    viewModel: ReportViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current

    // Load data awal
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
        viewModel.loadReport()
    }

    // STATE UNTUK DROPDOWN
    var expandedProduct by remember { mutableStateOf(false) }
    var expandedItem by remember { mutableStateOf(false) }
    var expandedType by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Laporan Stok") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            /* ================= FILTER PRODUK ================= */
            ExposedDropdownMenuBox(
                expanded = expandedProduct,
                onExpandedChange = { expandedProduct = !expandedProduct }
            ) {
                OutlinedTextField(
                    value = viewModel.selectedProduct?.product_name ?: "Semua Produk",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Produk") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProduct)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedProduct,
                    onDismissRequest = { expandedProduct = false }
                ) {
                    // Pilihan "Semua Produk"
                    DropdownMenuItem(
                        text = { Text("Semua Produk") },
                        onClick = {
                            viewModel.onProductSelected(null)
                            expandedProduct = false
                        }
                    )

                    // Looping data produk dari ViewModel
                    viewModel.products.forEach { product ->
                        DropdownMenuItem(
                            text = { Text(product.product_name) },
                            onClick = {
                                viewModel.onProductSelected(product)
                                expandedProduct = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            /* ================= FILTER ITEM ================= */
            // Item dropdown hanya aktif jika produk sudah dipilih dan items tersedia
            val isItemEnabled = viewModel.items.isNotEmpty()

            ExposedDropdownMenuBox(
                expanded = expandedItem,
                onExpandedChange = { if (isItemEnabled) expandedItem = !expandedItem }
            ) {
                OutlinedTextField(
                    value = viewModel.selectedItem?.let { "${it.size} - ${it.color}" } ?: "Semua Item",
                    onValueChange = {},
                    readOnly = true,
                    enabled = isItemEnabled,
                    label = { Text("Item Produk") },
                    trailingIcon = {
                        if (isItemEnabled) ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedItem)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedItem,
                    onDismissRequest = { expandedItem = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Semua Item") },
                        onClick = {
                            viewModel.onItemSelected(null)
                            expandedItem = false
                        }
                    )

                    viewModel.items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text("Size: ${item.size} | Warna: ${item.color}") },
                            onClick = {
                                viewModel.onItemSelected(item)
                                expandedItem = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            /* ================= FILTER TYPE ================= */
            ExposedDropdownMenuBox(
                expanded = expandedType,
                onExpandedChange = { expandedType = !expandedType }
            ) {
                OutlinedTextField(
                    value = viewModel.selectedType ?: "Semua Tipe",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Jenis Transaksi") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedType)
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedType,
                    onDismissRequest = { expandedType = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Semua Tipe") },
                        onClick = {
                            viewModel.onTypeSelected(null)
                            expandedType = false
                        }
                    )
                    listOf("IN", "OUT").forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                viewModel.onTypeSelected(type)
                                expandedType = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            /* ================= DATE PICKER ================= */
            Row(modifier = Modifier.fillMaxWidth()) {
                DatePickerField(
                    label = "Mulai",
                    selectedDate = viewModel.startDate,
                    onDateSelected = viewModel::onStartDateSelected,
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                DatePickerField(
                    label = "Akhir",
                    selectedDate = viewModel.endDate,
                    onDateSelected = viewModel::onEndDateSelected,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            /* ================= BUTTON FILTER ================= */
            Button(
                onClick = { viewModel.loadReport() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Terapkan Filter")
            }

            Spacer(Modifier.height(12.dp))

            /* ================= DOWNLOAD ================= */
            OutlinedButton(
                onClick = {

                    val baseUrl =
                        "http://10.0.2.2:8080/FASH_API/reports/report_export_pdf.php"

                    // 🔑 INI BAGIAN PENTING: bangun URL + filter
                    val uri = Uri.parse(baseUrl).buildUpon().apply {

                        viewModel.selectedProduct?.let {
                            appendQueryParameter(
                                "product_id",
                                it.product_id.toString()
                            )
                        }

                        viewModel.selectedItem?.let {
                            appendQueryParameter(
                                "item_id",
                                it.item_id.toString()
                            )
                        }

                        viewModel.selectedType?.let {
                            appendQueryParameter("type", it)
                        }

                        viewModel.startDate?.let {
                            appendQueryParameter("start_date", it.toString())
                        }

                        viewModel.endDate?.let {
                            appendQueryParameter("end_date", it.toString())
                        }

                    }.build()

                    // ⬇️ DownloadManager pakai URI di atas
                    val request = DownloadManager.Request(uri)
                        .setTitle("Laporan Stok")
                        .setDescription("Mengunduh laporan stok (PDF)")
                        .setNotificationVisibility(
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                        )
                        .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            "laporan_stok.pdf"
                        )

                    val dm = context.getSystemService(Context.DOWNLOAD_SERVICE)
                            as DownloadManager

                    dm.enqueue(request)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Download Laporan (PDF/Excel)")
            }

            Spacer(Modifier.height(16.dp))

            /* ================= LIST LAPORAN ================= */
            LazyColumn {
                items(viewModel.reports) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.movement_type == "IN")
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            else
                                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                        )
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.product_name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = item.movement_type,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (item.movement_type == "IN")
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.error
                                )
                            }

                            Text("${item.size} / ${item.color}")
                            Text("Jumlah: ${item.quantity}")
                            Text("Alasan: ${item.reason}")
                            if (!item.note.isNullOrEmpty()) {
                                Text("Catatan: ${item.note}", style = MaterialTheme.typography.bodySmall)
                            }
                            Text(
                                text = item.created_at,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
   DATE PICKER (HELPER)
   ========================================================= */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()

    OutlinedTextField(
        value = selectedDate?.toString() ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        modifier = modifier.clickable { showDialog = true }, // Tambahkan clickable di sini juga
        enabled = false, // Supaya keyboard tidak muncul, tapi klik ditangkap box
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, null)
            }
        }
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = state.selectedDateMillis
                    if (millis != null) {
                        onDateSelected(
                            Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        )
                    }
                    showDialog = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = state)
        }
    }
}