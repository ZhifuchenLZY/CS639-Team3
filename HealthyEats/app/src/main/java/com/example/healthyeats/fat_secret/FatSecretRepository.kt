package com.example.healthyeats.fat_secret

import android.util.Base64
import com.google.gson.annotations.SerializedName

/**
 * token response from https://oauth.fatsecret.com/connect/token
 */
data class AccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
)

class FatSecretRepository(private val fatSecretApi: FatSecretApi) {

    suspend fun requestAccessToken(
        clientId: String,
        clientSecret: String,
        scope: String,
        grantType: String
    ): AccessTokenResponse {
        val authHeader = "Basic " + Base64.encodeToString(
            "$clientId:$clientSecret".toByteArray(),
            Base64.NO_WRAP
        )
        val body = mapOf(
            "grant_type" to grantType,
            "scope" to scope
        )
        return fatSecretApi.requestAccessToken(authHeader, body)
    }
}
