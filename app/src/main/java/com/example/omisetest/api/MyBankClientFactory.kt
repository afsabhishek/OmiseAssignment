package com.example.omisetest.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MyBankClientFactory {
  fun create(baseUrl: String): MyBankConnector
}

object MyBankReleaseClientFactory : MyBankClientFactory {

  override fun create(baseUrl: String) = MyBankConnector(
    Retrofit.Builder().apply {
      baseUrl(baseUrl)
      addConverterFactory(GsonConverterFactory.create())
      client(buildHttpClient())
    }
      .build()
      .create(MyBankClient::class.java)
  )

  private fun buildHttpClient(vararg interceptors: Interceptor) = OkHttpClient.Builder().apply {
    interceptors().apply {
      interceptors.forEach { add(it) }
    }
  }.build()
}

fun buildMyBank(baseUrl: String): MyBankConnector =
  MyBankReleaseClientFactory.create(baseUrl)
