package com.example.healthyeats

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class FatSecretApiClient(
    private val clientId: String,
    private val clientSecret: String,
    private val tokenUrl: String = "https://oauth.fatsecret.com/oauth2/token",
    private val apiBaseUrl: String = "https://platform.fatsecret.com/rest/server.api"
) {
    private val client = OkHttpClient()
    private var accessToken: String? = null

    // get access token
    suspend fun getAccessToken(): String? {
        val formBody = FormBody.Builder()
            .add("grant_type", "client_credentials")
            .build()

        val request = Request.Builder()
            .url(tokenUrl)
            .post(formBody)
            .header("Authorization", "Basic ${base64Encode("$clientId:$clientSecret")}")
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string() ?: return null
                parseAccessTokenResponse(responseBody)
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    //    Base64 encoding
    private fun base64Encode(input: String): String {
        return android.util.Base64.encodeToString(input.toByteArray(), android.util.Base64.NO_WRAP)
    }

    //    Parse access token responses
    private fun parseAccessTokenResponse(json: String): String? {
        return try {
            val jsonObject = org.json.JSONObject(json)
            jsonObject.getString("access_token")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // query food nutrition
    suspend fun searchFood(query: String, accessToken: String): String? {
        val url = "$apiBaseUrl?method=foods.search&search_expression=$query&format=json"

        val request = Request.Builder()
            .url(url)
            .get()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // get access token and query food nutrition
    suspend fun searchFoodWithToken(query: String): String? {
        val token = getAccessToken() ?: return null
        return searchFood(query, token)
    }
}