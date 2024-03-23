package com.bhavesh.weatherappindianic.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException


class BasicAuthInterceptor(user: String?, password: String?) : Interceptor {
    private val credentials: String

    init {
        credentials = Credentials.basic(user!!, password!!)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest = request.newBuilder().header("Authorization", credentials).method(request.method,request.body).build()
        return chain.proceed(authenticatedRequest)
    }
}