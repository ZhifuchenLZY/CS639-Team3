package com.example.healthyeats.fat_secret

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// Define the API interface for FatSecret
interface FatSecretApi {
    /**
     * request fatsecret token
     */
    @POST("connect/token")
    suspend fun requestAccessToken(
        @Header("Authorization") authHeader: String,
        @Body body: Map<String, String>
    ): AccessTokenResponse

    companion object {
        fun create(httpUrl: HttpUrl): FatSecretApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    // Enable basic HTTP logging to help with debugging.
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FatSecretApi::class.java)
        }
    }
}