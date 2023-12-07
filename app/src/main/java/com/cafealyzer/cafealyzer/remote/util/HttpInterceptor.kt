package com.cafealyzer.cafealyzer.remote.util

import com.cafealyzer.cafealyzer.local.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : Interceptor {

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
        redactHeader("Authorization")
        redactHeader("Cookie")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStoreManager.getToken() }
        val requestBuilder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
        // return loggingInterceptor.intercept(chain)
    }
}
