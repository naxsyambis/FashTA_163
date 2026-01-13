package com.example.fashta_163.modeldata
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val category_id: Int = 0,
    val category_name: String = ""
)

@Serializable
data class ApiMessage(
    val message: String
)
