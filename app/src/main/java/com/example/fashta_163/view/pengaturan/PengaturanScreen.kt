package com.example.fashta_163.view.pengaturan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fashta_163.uicontroller.route.DestinasiKelolaKategori
import com.example.fashta_163.uicontroller.route.DestinasiLogin


private val SettingBgColor = Color(0xFFF9F5F0)
private val SettingTextPrimary = Color(0xFF5D4037)
private val SettingSurface = Color(0xFFFFFFFF)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengaturanScreen(
    navController: NavController
) {
    Scaffold(
        containerColor = SettingBgColor
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pengaturan",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = SettingTextPrimary
                )
            )
            Text(
                text = "Kelola preferensi akun aplikasi",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
            )


            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {


                SettingItemCard(
                    title = "Kelola Kategori",
                    icon = Icons.Default.Category,
                    onClick = {
                        navController.navigate(DestinasiKelolaKategori.route)
                    }
                )


                SettingItemCard(
                    title = "Logout",
                    icon = Icons.Default.Logout,
                    isDestructive = true,
                    onClick = {
                        navController.navigate(DestinasiLogin.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingItemCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SettingSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isDestructive) Color(0xFFFFEBEE) else Color(0xFFEFEBE9)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isDestructive) Color.Red else SettingTextPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Title Text
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = if (isDestructive) Color.Red else SettingTextPrimary,
                modifier = Modifier.weight(1f)
            )

            // Chevron Arrow (Only show if not logout/destructive for cleaner look, or always show)
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}