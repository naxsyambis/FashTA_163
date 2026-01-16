package com.example.fashta_163.view.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fashta_163.viewmodel.stock.StockViewModel
import com.example.fashta_163.modeldata.StockReason

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockForm(
    itemId: Int,
    type: String,
    viewModel: StockViewModel,
    onSuccess: () -> Unit
) {
    var qty by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // ===== DROPDOWN ALASAN (SRS ENUM) =====
    val reasons = StockReason.values().toList()
    var expanded by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf(StockReason.PURCHASE) }

    Column(Modifier.padding(16.dp)) {

        // ===== ERROR MESSAGE =====
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(8.dp))
        }

        // ===== INPUT JUMLAH =====
        OutlinedTextField(
            value = qty,
            onValueChange = { input ->
                if (input.all(Char::isDigit)) qty = input
            },
            label = { Text("Jumlah") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // ===== DROPDOWN ALASAN =====
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedReason.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Alasan") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                reasons.forEach { reason ->
                    DropdownMenuItem(
                        text = { Text(reason.name) },
                        onClick = {
                            selectedReason = reason
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ===== BUTTON SIMPAN =====
        Button(
            onClick = {
                val qtyInt = qty.toIntOrNull()
                if (qtyInt == null || qtyInt <= 0) {
                    errorMessage = "Jumlah stok harus diisi"
                    return@Button
                }

                viewModel.submitStock(
                    itemId = itemId,
                    type = type,
                    qty = qtyInt,
                    reason = selectedReason.name,
                    note = null,
                    onError = { errorMessage = it },
                    onSuccess = onSuccess
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}
