package com.example.fashta_163.view.item

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fashta_163.modeldata.DetailItemProduct

@Composable
fun ItemProductForm(
    modifier: Modifier = Modifier,
    detail: DetailItemProduct,
    onValueChange: (DetailItemProduct) -> Unit,
    isButtonEnabled: Boolean,
    buttonText: String,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        OutlinedTextField(
            value = detail.size,
            onValueChange = { onValueChange(detail.copy(size = it)) },
            label = { Text("Ukuran*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = detail.color,
            onValueChange = { onValueChange(detail.copy(color = it)) },
            label = { Text("Warna*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = detail.price,
            onValueChange = { onValueChange(detail.copy(price = it)) },
            label = { Text("Harga*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = detail.size,
            onValueChange = {
                onValueChange(
                    detail.copy(size = it)
                )
            },
            label = { Text("Size*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSubmit,
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(buttonText)
        }
    }
}
