package com.example.fashta_163.apiservice

import com.example.fashta_163.modeldata.AuthResponse
import com.example.fashta_163.modeldata.DataAdmin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceApiAuth {

    @POST("auth/register.php")
    suspend fun register(
        @Body data: DataAdmin
    ): AuthResponse

    @POST("auth/login.php")
    suspend fun login(
        @Body data: DataAdmin
    ): AuthResponse
}
