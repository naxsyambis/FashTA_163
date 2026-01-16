package com.example.fashta_163.view.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fashta_163.viewmodel.stock.StockViewModel

@Composable
fun StockForm(
    itemId: Int,
    type: String,
    viewModel: StockViewModel,
    onSuccess: () -> Unit
) {
    var qty by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = qty,
            onValueChange = { if (it.all(Char::isDigit)) qty = it },
            label = { Text("Jumlah") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.submitStock(
                    itemId = itemId,
                    type = type,
                    qty = qty.toInt(),
                    reason = "ADJUSTMENT",
                    note = null,
                    onSuccess = onSuccess
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}
