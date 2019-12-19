package com.example.omisetest.api

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object AndroidEncryptionClient {

  private const val keyAlias = "omise"
  private const val outputKeyLength = 256

  private const val keyStoreName = "AndroidKeyStore"
  private val api23andOver = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

  private val algorithm = if (api23andOver) { KeyProperties.KEY_ALGORITHM_AES } else { "AES" }
  private val blockMode = if (api23andOver) { KeyProperties.BLOCK_MODE_GCM } else { "GCM" }
  private val padding = if (api23andOver) { KeyProperties.ENCRYPTION_PADDING_NONE } else { "NoPadding" }

  private val transform = "$algorithm/$blockMode/$padding"

  private val keyStore = KeyStore.getInstance(keyStoreName).apply { load(null) }

  private val secretKey = try {
    retrieveSecretKey()
  } catch (ex: Exception) {
    createSecretKey()
  }

  private val encryptCipher = buildCipher(Cipher.ENCRYPT_MODE, secretKey)
  private val decryptCipher = buildCipher(Cipher.DECRYPT_MODE, secretKey)

  private fun retrieveSecretKey() =
    (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey

  private fun createSecretKey() = generateKey().apply {
    val entry = KeyStore.SecretKeyEntry(this)
    keyStore.setEntry(keyAlias, entry, null)
  }

  private fun generateKey() = KeyGenerator.getInstance(algorithm, keyStoreName).run {
    when {
      api23andOver -> init(
        KeyGenParameterSpec.Builder(
          keyAlias,
          KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
          .setBlockModes(blockMode)
          .setEncryptionPaddings(padding)
          .build()
      )
      else -> init(outputKeyLength, SecureRandom())
    }
    generateKey()
  }

  private fun buildCipher(mode: Int, secretKey: SecretKey) = Cipher.getInstance(transform).apply {
    init(mode, secretKey)
  }

  val String.encrypted: ByteArray
    get() = encryptCipher.doFinal(toByteArray(Charsets.UTF_8))

  val String.asBase64Bytes: ByteArray
    get() = Base64.decode(this, Base64.DEFAULT)

  val ByteArray.base64: String
    get() = Base64.encodeToString(this, Base64.DEFAULT)

  val ByteArray.decrypted: String
    get() = String(decryptCipher.doFinal(this), Charsets.UTF_8)
}
