package com.example.fashta_163.uicontroller.route

import com.example.fashta_163.R


object DestinasiStockMenu : DestinasiNavigasi {
    override val route = "stock_menu/{itemId}"
    override val titleRes = R.string.menu_stock
    const val itemIdArg = "itemId"

    fun createRoute(itemId: Int) = "stock_menu/$itemId"
}

object DestinasiStockIn : DestinasiNavigasi {
    override val route = "stock_in/{itemId}"
    override val titleRes = R.string.stock_in
    const val itemIdArg = "itemId"

    fun createRoute(itemId: Int) = "stock_in/$itemId"
}

object DestinasiStockOut : DestinasiNavigasi {
    override val route = "stock_out/{itemId}"
    override val titleRes = R.string.stock_out
    const val itemIdArg = "itemId"

    fun createRoute(itemId: Int) = "stock_out/$itemId"
}