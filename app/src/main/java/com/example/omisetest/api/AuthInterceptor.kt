package com.example.omisetest.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
  private val getToken: () -> String?
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response =
    chain.proceed(
      chain
        .request()
        .newBuilder().apply {
          val auth = getToken()
          if (auth != null) {
            header("Authorization", "Bearer $auth")
          }
        }.build()
    )
}
