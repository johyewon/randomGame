package com.hanix.randomgame.common.utils

import android.annotation.SuppressLint
import com.hanix.randomgame.common.app.GLog.e
import org.apache.commons.codec.binary.Base64
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex
import java.nio.charset.StandardCharsets
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 암복호화 유틸
 */
object SecretUtils {

    private var keyData: ByteArray? = null
    private var iv: ByteArray? = null

    fun setKeyDataDecodeStr(decodeStr: String?) {
        val keyDataDecodeStr =
            Utils.byteArrayToHexString(Utils.addPaddingStr(decodeStr, 16).toByteArray())
        keyData = Hex.decode(keyDataDecodeStr)
    }

    fun setIvDataDecodeStr(decodeStr: String?) {
        val ivDataDecodeStr =
            Utils.byteArrayToHexString(Utils.addPaddingStr(decodeStr, 16).toByteArray())
        iv = Hex.decode(ivDataDecodeStr)
    }

    /** 복호화  */
    @SuppressLint("GetInstance")
    fun getDecStr(encStr: String): String {
        var decStr = ""

        try {
            Security.addProvider(BouncyCastleProvider())

            val secureKey: SecretKey = SecretKeySpec(keyData, "AES")
            val c = Cipher.getInstance("AES/CBC/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME)
            c.init(Cipher.DECRYPT_MODE, secureKey, IvParameterSpec(iv))

            val enc = Base64.decodeBase64(encStr.toByteArray())
            decStr = String(c.doFinal(enc), StandardCharsets.UTF_8)

        } catch (e: Exception) {
            e(e.message, e)
        }
        return decStr
    }

    /** 암호화  */
    @SuppressLint("GetInstance")
    fun getEncStr(plainStr: String): String {
        var encStr = ""

        try {
            Security.addProvider(BouncyCastleProvider())

            val secureKey: SecretKey = SecretKeySpec(keyData, "AES")
            val c = Cipher.getInstance("AES/CBC/PKCS5Padding", BouncyCastleProvider.PROVIDER_NAME)
            c.init(Cipher.ENCRYPT_MODE, secureKey, IvParameterSpec(iv))

            val results = c.doFinal(plainStr.toByteArray())
            encStr = String(Base64.encodeBase64(results), StandardCharsets.UTF_8)

        } catch (e: Exception) {
            e(e.message, e)
        }
        return encStr
    }
}