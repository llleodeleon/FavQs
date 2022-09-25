package com.leodeleon.favqs.data

import com.leodeleon.favqs.BuildConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val prefsRepo: IPrefsRepo) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder().apply {
            addHeader("Authorization", "Token token=${BuildConfig.favqApiKey}")
            runBlocking {
                val token = prefsRepo.appPrefsFlow.first().token
                if (token.isNotEmpty()) {
                    addHeader("User-Token", token)
                }
            }
        }
        return chain.proceed(builder.build())
    }
}