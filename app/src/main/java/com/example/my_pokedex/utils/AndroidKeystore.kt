package com.example.my_pokedex.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object AndroidKeyStore {
    const val ANDROID_KEY_STORE = "AndroidKeyStore"
    const val AES_MODE = "AES/GCM/NoPadding"
    private var iv: ByteArray = byteArrayOf(55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44)

    const val SECRET_ALIAS = "SECRET_ALIAS"

    private fun generateSecretKey(keyAlias: String): SecretKey {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        val spec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(false)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    private fun getSecretKey(keyAlias: String): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }
        if (keyStore.getEntry(keyAlias, null) != null) {
            val secretKeyEntry = keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
            return secretKeyEntry.secretKey ?: generateSecretKey(keyAlias)
        }
        return generateSecretKey(keyAlias)
    }

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(SECRET_ALIAS), GCMParameterSpec(128, iv))
        iv = cipher.iv
        val encodedBytes = cipher.doFinal(data.toByteArray())
        return android.util.Base64.encodeToString(encodedBytes, android.util.Base64.NO_WRAP)
    }


    fun decrypt(encrypted: String): String {
        return try {
            val cipher = Cipher.getInstance(AES_MODE)
            val spec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(SECRET_ALIAS), spec)
            val encodedBytes = android.util.Base64.decode(encrypted, android.util.Base64.NO_WRAP)
            val decoded = cipher.doFinal(encodedBytes)
            String(decoded, Charsets.UTF_8)
        }catch (e : Exception){
            ""
        }

    }

}