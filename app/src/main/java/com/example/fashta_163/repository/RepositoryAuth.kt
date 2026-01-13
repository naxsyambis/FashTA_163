package com.example.fashta_163.repository

import com.example.fashta_163.apiservice.ServiceApiAuth
import com.example.fashta_163.modeldata.AuthResponse
import com.example.fashta_163.modeldata.DataAdmin
import retrofit2.Response

interface RepositoryAuth {

    suspend fun login(dataAdmin: DataAdmin): AuthResponse

    suspend fun register(dataAdmin: DataAdmin): AuthResponse
}

class JaringanRepositoryAuth(
    private val serviceApiAuth: ServiceApiAuth
) : RepositoryAuth {

    override suspend fun login(dataAdmin: DataAdmin): AuthResponse =
        serviceApiAuth.login(dataAdmin)

    override suspend fun register(dataAdmin: DataAdmin): AuthResponse =
        serviceApiAuth.register(dataAdmin)
}
