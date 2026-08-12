package com.accounting.app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonFactory

object NetworkModule {
    private const val BASE_URL = "https://accounting-system-backend-production-97e3.up.railway.app/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
