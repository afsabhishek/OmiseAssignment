package com.example.omisetest.api

class StorageConnector(private val client: AndroidStorageClient) {

  private fun store(key: String, value: String) {
    client.store(key, value)
  }
}
