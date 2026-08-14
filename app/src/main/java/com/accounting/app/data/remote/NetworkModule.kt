package com.accounting.app.data.remote

import android.content.Context
import com.accounting.app.data.auth.SessionStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://accounting-system-backend-production-97e3.up.railway.app/api/v1/"

    lateinit var apiService: ApiService
        private set

    lateinit var sessionStore: SessionStore
        private set

    fun initialize(context: Context) {
        sessionStore = SessionStore(context.applicationContext)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val accessToken = sessionStore.accessToken
                val authenticated = original.newBuilder()
                    .apply {
                        if (!accessToken.isNullOrBlank()) {
                            header("Authorization", "Bearer $accessToken")
                        }
                    }
                    .build()
                chain.proceed(authenticated)
            }
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
