package com.example.fashta_163.view.produk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fashta_163.viewmodel.pengaturan.CategoryReadViewModel
import com.example.fashta_163.viewmodel.pengaturan.StatusUiCategory

// --- Warna Lokal (Private agar tidak bentrok) ---
private val DropdownPrimaryColor = Color(0xFF5D4037) // Cokelat Tua
private val DropdownInputBg = Color(0xFFFFFFFF)      // Putih

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categoryViewModel: CategoryReadViewModel,
    selectedCategoryId: Int,
    onCategorySelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    when (val state = categoryViewModel.statusUiCategory) {

        is StatusUiCategory.Success -> {

            val selectedCategory =
                state.listCategory.firstOrNull {
                    it.category_id == selectedCategoryId
                }?.category_name ?: "Pilih Kategori"

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    // --- STYLING AGAR SESUAI TEMA ---
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DropdownPrimaryColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = DropdownPrimaryColor,
                        cursorColor = DropdownPrimaryColor,
                        focusedContainerColor = DropdownInputBg,
                        unfocusedContainerColor = DropdownInputBg,
                        focusedTrailingIconColor = DropdownPrimaryColor,
                        unfocusedTrailingIconColor = Color.Gray
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(DropdownInputBg) // Latar menu putih
                ) {
                    state.listCategory.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = category.category_name,
                                    color = DropdownPrimaryColor // Teks menu cokelat
                                )
                            },
                            onClick = {
                                onCategorySelected(category.category_id)
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }

        is StatusUiCategory.Loading -> {
            // Tampilan Loading yang lebih rapi (seperti input field disabled)
            OutlinedTextField(
                value = "Memuat kategori...",
                onValueChange = {},
                readOnly = true,
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = Color(0xFFEEEEEE),
                    disabledBorderColor = Color.Transparent
                )
            )
        }

        is StatusUiCategory.Error -> {
            Text(
                text = "Gagal memuat kategori",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}