package com.example.fashta_163.repository

import android.app.Application
import com.example.fashta_163.apiservice.ServiceApiAuth
import com.example.fashta_163.apiservice.ServiceApiCategory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface ContainerApp {
    val repositoryAuth: RepositoryAuth
    val repositoryCategory: RepositoryCategory
}

class DefaultContainerApp : ContainerApp {

    private val baseUrl = "http://10.0.2.2:8080/FASH_API/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .client(client)
        .build()

    private val retrofitServiceAuth: ServiceApiAuth by lazy {
        retrofit.create(ServiceApiAuth::class.java)
    }

    override val repositoryAuth: RepositoryAuth by lazy {
        JaringanRepositoryAuth(retrofitServiceAuth)
    }

    private val retrofitServiceCategory: ServiceApiCategory by lazy {
        retrofit.create(ServiceApiCategory::class.java)
    }

    override val repositoryCategory: RepositoryCategory by lazy {
        JaringanRepositoryCategory(retrofitServiceCategory)
    }

}

class AplikasiFash : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp = DefaultContainerApp()
    }
}
