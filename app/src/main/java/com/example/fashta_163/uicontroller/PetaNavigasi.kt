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
import com.example.fashta_163.view.item.ItemProductEditScreen
import com.example.fashta_163.view.item.ItemProductEntryScreen
import com.example.fashta_163.view.item.ItemProductScreen
import com.example.fashta_163.view.pengaturan.PengaturanScreen
import com.example.fashta_163.view.produk.ProductCreateScreen
import com.example.fashta_163.view.produk.ProductEditScreen
import com.example.fashta_163.view.produk.ProductScreen
import com.example.fashta_163.view.stock.StockInScreen
import com.example.fashta_163.view.stock.StockMenuScreen
import com.example.fashta_163.view.stock.StockOutScreen
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

        // ===== DASHBOARD =====
        composable(DestinasiDashboard.route) {
            DashboardScreen(
                onNavigateToPengaturan = {
                    navController.navigate(DestinasiPengaturan.route)
                },
                onNavigateToProduct = {
                    navController.navigate(DestinasiProduct.route)
                },
                onNavigateToStock = {
                    navController.navigate(DestinasiStockMenu.route)
                }
            )
        }

        // ===== PENGATURAN =====
        composable(DestinasiPengaturan.route) {
            PengaturanScreen(navController = navController)
        }

        // ===== PRODUCT =====
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
            route = DestinasiProductEdit.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) {
            ProductEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // ===== ITEM PRODUCT =====
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
                },
                navigateToStock = { itemId ->
                    navController.navigate(
                        DestinasiStockMenu.createRoute(itemId)
                    )
                }
            )
        }

        composable(
            route = DestinasiItemProdukCreate.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId =
                backStackEntry.arguments?.getInt("productId") ?: 0

            ItemProductEntryScreen(
                productId = productId,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiItemProdukEdit.route,
            arguments = listOf(
                navArgument(DestinasiItemProdukEdit.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            ItemProductEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // ===== STOCK =====
        composable(DestinasiStockMenu.route) {
            StockMenuScreen(
                itemId = 0,
                navigateToStockIn = { itemId ->
                    navController.navigate(
                        DestinasiStockIn.createRoute(itemId)
                    )
                },
                navigateToStockOut = { itemId ->
                    navController.navigate(
                        DestinasiStockOut.createRoute(itemId)
                    )
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiStockIn.route,
            arguments = listOf(
                navArgument(DestinasiStockIn.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val itemId =
                backStackEntry.arguments?.getInt(
                    DestinasiStockIn.itemIdArg
                ) ?: 0

            StockInScreen(
                itemId = itemId,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiStockOut.route,
            arguments = listOf(
                navArgument(DestinasiStockOut.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val itemId =
                backStackEntry.arguments?.getInt(
                    DestinasiStockOut.itemIdArg
                ) ?: 0

            StockOutScreen(
                itemId = itemId,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}