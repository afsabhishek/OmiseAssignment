package com.example.omisetest.api

import android.content.Context
import com.example.omisetest.api.AndroidEncryptionClient.asBase64Bytes
import com.example.omisetest.api.AndroidEncryptionClient.base64
import com.example.omisetest.api.AndroidEncryptionClient.decrypted
import com.example.omisetest.api.AndroidEncryptionClient.encrypted

class AndroidStorageClient(getContext: () -> Context) {

  private val prefs = getContext().getSharedPreferences(storeName, Context.MODE_PRIVATE)

  private fun storeInsecure(key: String, value: String) {
    prefs.edit().apply {
      putString(key, value)
      apply()
    }
  }

  private fun retrieveInsecure(key: String): String? = prefs.getString(key, null)

  fun store(key: String, value: String) = storeInsecure(key, value.encrypted.base64)

  fun retrieve(key: String): String? = retrieveInsecure(key)?.asBase64Bytes?.decrypted

  companion object {
    private const val storeName = "omise_secure_prefs"
  }
}
