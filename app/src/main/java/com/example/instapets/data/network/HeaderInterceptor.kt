package com.example.instapets.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "x-api-key",
                "live_cWMe49yozJb3zrx0qg4F128kyhXbRsRHkGt7q436gX0sQv3bvfX3LLl9xicAnlQT"
            )
            .build()
        return chain.proceed(request)
    }

}
