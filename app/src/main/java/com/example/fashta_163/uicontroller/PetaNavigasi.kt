package com.example.fashta_163.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.fashta_163.uicontroller.route.*
import com.example.fashta_163.view.auth.LoginScreen
import com.example.fashta_163.view.auth.RegisterScreen
import com.example.fashta_163.view.dashboard.DashboardScreen
import com.example.fashta_163.view.item.ItemProductScreen
import com.example.fashta_163.view.pengaturan.PengaturanScreen
import com.example.fashta_163.view.produk.ProductCreateScreen
import com.example.fashta_163.view.produk.ProductEditScreen
import com.example.fashta_163.view.produk.ProductScreen
import com.example.fashta_163.viewmodel.LoginViewModel
import com.example.fashta_163.viewmodel.RegisterViewModel
import com.example.fashta_163.viewmodel.provider.PenyediaViewModel

@Composable
fun FashApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route,
        modifier = modifier
    ) {

        // ===== LOGIN =====
        composable(DestinasiLogin.route) {
            val loginViewModel: LoginViewModel =
                viewModel(factory = PenyediaViewModel.Factory)

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(DestinasiDashboard.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(DestinasiRegister.route)
                }
            )
        }

        // ===== REGISTER =====
        composable(DestinasiRegister.route) {
            val registerViewModel: RegisterViewModel =
                viewModel(factory = PenyediaViewModel.Factory)

            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(DestinasiPengaturan.route) {
            PengaturanScreen(navController = navController)
        }

        // ===== DASHBOARD (sementara placeholder) =====
        composable(DestinasiDashboard.route) {
            DashboardScreen(
                onNavigateToPengaturan = {
                    navController.navigate(DestinasiPengaturan.route)
                },
                onNavigateToProduct = { navController.navigate(DestinasiProduct.route) }
            )
        }

        composable(DestinasiPengaturan.route) {
            PengaturanScreen(navController = navController)
        }

        composable(DestinasiKelolaKategori.route) {
            KelolaKategoriScreen()
        }

        composable("product_home") {
            ProductScreen(
                navigateToAdd = {
                    navController.navigate(DestinasiProductCreate.route)
                },
                navigateToEdit = { product ->
                    navController.navigate(
                        DestinasiProductEdit.createRoute(product.product_id)
                    )
                },
                navigateToItemProduct = { productId ->
                    navController.navigate(
                        DestinasiItemProduk.createRoute(productId)
                    )
                }
            )
        }

        composable(DestinasiProductCreate.route) {
            ProductCreateScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiItemProduk.route,
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
            })
        ) {
            ItemProductScreen(
                navigateToAdd = { productId ->
                    navController.navigate(
                        DestinasiItemProdukCreate.createRoute(productId)
                    )
                },
                navigateToEdit = { itemId ->
                    navController.navigate(
                        DestinasiItemProdukEdit.createRoute(itemId)
                    )
                }
            )
        }

        composable(
            route = "product_edit/{productId}",
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
            })
        ) {
            ProductEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}
