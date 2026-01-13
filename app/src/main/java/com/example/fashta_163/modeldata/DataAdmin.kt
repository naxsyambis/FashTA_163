package com.example.fashta_163.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataAdmin(
    val id: Int = 0,
    val username: String = "",
    val password: String = ""
)

@Serializable
data class AuthResponse(
    val status: Boolean,
    val message: String
)

data class UIStateAuth(
    val detailAuth: DetailAuth = DetailAuth(),
    val isEntryValid: Boolean = false,
    val errorMessage: String? = null
)


data class DetailAuth(
    val username: String = "",
    val password: String = ""
)


fun DetailAuth.toDataAdmin(): DataAdmin = DataAdmin(
    username = username,
    password = password
)

fun DataAdmin.toUiStateAuth(isEntryValid: Boolean = false): UIStateAuth =
    UIStateAuth(
        detailAuth = this.toDetailAuth(),
        isEntryValid = isEntryValid
    )

fun DataAdmin.toDetailAuth(): DetailAuth = DetailAuth(
    username = username,
    password = password
)
