package com.fitoscan.ai

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiTransport {
    private val client = OkHttpClient.Builder()
        .connectTimeout(NetworkConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NetworkConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(NetworkConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    fun postJson(jsonBody: String, callback: (Result<String>) -> Unit) {
        val request = Request.Builder()
            .url(NetworkConfig.BASE_URL + NetworkConfig.ANALYZE_PATH)
            .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = callback(Result.failure(e))
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val body = it.body?.string().orEmpty()
                    if (it.isSuccessful) callback(Result.success(body))
                    else callback(Result.failure(IOException("HTTP ${it.code}: $body")))
                }
            }
        })
    }
}
