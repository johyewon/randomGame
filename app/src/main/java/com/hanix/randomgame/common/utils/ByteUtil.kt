package com.hanix.randomgame.common.utils

import okhttp3.internal.and

object ByteUtil {
    //public static Byte DEFAULT_BYTE = new Byte((byte) 0);
    /**
     *
     * unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
     *
     * <pre>
     * ByteUtils.toHexString(null)                   = null
     * ByteUtils.toHexString([(byte)1, (byte)255])   = "01ff"
    </pre> *
     *
     * @param bytes unsigned byte's array
     * @return
     */
    fun toHexString(bytes: ByteArray?): String? {
        if (bytes == null) {
            return null
        }

        val result = StringBuilder()

        for (b in bytes) {
            result.append((b and 0xF0 shr 4).toString(16))
            result.append((b and 0x0F).toString(16))
        }

        return result.toString()
    }

    /**
     *
     * 8, 10, 16진수 문자열을 바이트 배열로 변환한다.
     *
     * 8, 10진수인 경우는 문자열의 3자리가, 16진수인 경우는 2자리가, 하나의 byte 로 바뀐다.
     *
     * <pre>
     * ByteUtils.toBytes(null)     = null
     * ByteUtils.toBytes("0E1F4E", 16) = [0x0e, 0xf4, 0x4e]
     * ByteUtils.toBytes("48414e", 16) = [0x48, 0x41, 0x4e]
    </pre> *
     *
     * @param digits 문자열
     * @param radix 진수(8, 10, 16만 가능)
     * @return
     * @throws NumberFormatException
     */
    @kotlin.jvm.Throws(IllegalArgumentException::class)
    fun toBytes(digits: String?, radix: Int): ByteArray? {
        if (digits == null)
            return null

        require(!(radix != 16 && radix != 10 && radix != 8)) { "For input radix: \"$radix\"" }

        val divLen = if (radix == 16) 2 else 3
        var length = digits.length

        require(length % divLen != 1) { "For input string: \"$digits\"" }

        length /= divLen

        val bytes = ByteArray(length)

        for (i in 0 until length) {
            val index = i * divLen
            bytes[i] = digits.substring(index, index + divLen).toShort(radix).toByte()
        }

        return bytes
    }

    /**
     *
     * 16진수 문자열을 바이트 배열로 변환한다.
     *
     * 문자열의 2자리가 하나의 byte 로 바뀐다.
     *
     * <pre>
     * ByteUtils.toBytesFromHexString(null)     = null
     * ByteUtils.toBytesFromHexString("0E1F4E") = [0x0e, 0xf4, 0x4e]
     * ByteUtils.toBytesFromHexString("48414e") = [0x48, 0x41, 0x4e]
    </pre> *
     *
     * @param digits 16진수 문자열
     * @return
     * @throws NumberFormatException
     */
    @kotlin.jvm.Throws(IllegalArgumentException::class)
    fun toBytesFromHexString(digits: String?): ByteArray? {
        if (digits == null)
            return null

        var length = digits.length

        require(length % 2 != 1) { "For input string: \"$digits\"" }
        length /= 2

        val bytes = ByteArray(length)

        for (i in 0 until length) {
            val index = i * 2
            bytes[i] = digits.substring(index, index + 2).toShort(16).toByte()
        }
        return bytes
    }
}