package com.hanix.randomgame.common.utils

import okhttp3.internal.and
import java.util.*

/**
 * 클래스명 : Base64Coder
 * 설명 : Base64Coding 을 담당하는 클래스
 * 주의사항 :
 */
object Base64Coder {
    private val SYSTEM_LINE_SEPARATOR = System.getProperty("line.separator")
    private val map1 = CharArray(64)
    private val map2 = ByteArray(128)

    fun encodeString(s: String): String {
        return String(encode(s.toByteArray()))
    }

    @JvmOverloads
    fun encodeLines(
        `in`: ByteArray,
        iOff: Int = 0,
        iLen: Int = `in`.size,
        lineLen: Int = 76,
        lineSeparator: String = SYSTEM_LINE_SEPARATOR
    ): String {
        val blockLen = lineLen * 3 / 4

        require(blockLen > 0)

        val lines = (iLen + blockLen - 1) / blockLen
        val bufLen = (iLen + 2) / 3 * 4 + lines * lineSeparator.length
        val buf = StringBuilder(bufLen)
        var ip = 0

        while (ip < iLen) {
            val l = Math.min(iLen - ip, blockLen)
            buf.append(encode(`in`, iOff + ip, l))
            //buf.append (lineSeparator);
            ip += l
        }

        return buf.toString()
    }

    @JvmName("encode1")
    fun encode(`in`: ByteArray, iLen: Int): CharArray {
        return encode(`in`, 0, iLen)
    }

    @JvmOverloads
    fun encode(`in`: ByteArray, iOff: Int = 0, iLen: Int = `in`.size): CharArray {
        val oDataLen = (iLen * 4 + 2) / 3 // output length without padding
        val oLen = (iLen + 2) / 3 * 4 // output length including padding

        val out = CharArray(oLen)
        var ip = iOff
        val iEnd = iOff + iLen
        var op = 0

        while (ip < iEnd) {
            val i0: Int = `in`[ip++] and 0xff

            val i1 = if (ip < iEnd) `in`[ip++] and 0xff else 0
            val i2 = if (ip < iEnd) `in`[ip++] and 0xff else 0

            val o0 = i0 ushr 2
            val o1 = i0 and 3 shl 4 or (i1 ushr 4)
            val o2 = i1 and 0xf shl 2 or (i2 ushr 6)
            val o3 = i2 and 0x3F

            out[op++] = map1[o0]
            out[op++] = map1[o1]

            out[op] = if (op < oDataLen) map1[o2] else '='
            op++

            out[op] = if (op < oDataLen) map1[o3] else '='
            op++
        }
        return out
    }

    fun decodeString(s: String): String {
        return String(decode(s))
    }

    fun decodeLines(s: String): ByteArray {
        val buf = CharArray(s.length)
        var p = 0

        for (element in s) {
            if (element != ' ' && element != '\r' && element != '\n' && element != '\t')
                buf[p++] = element
        }

        return decode(buf, 0, p)
    }

    private fun decode(s: String): ByteArray {
        return decode(s.toCharArray())
    }

    @JvmOverloads
    fun decode(`in`: CharArray, iOff: Int = 0, iLength: Int = `in`.size): ByteArray {
        var iLen = iLength

        require(iLen % 4 == 0) { "Length of Base64 encoded input string is not a multiple of 4." }

        while (iLen > 0 && `in`[iOff + iLen - 1] == '=') {
            iLen--
        }

        val oLen = iLen * 3 / 4
        val out = ByteArray(oLen)
        var ip = iOff
        val iEnd = iOff + iLen
        var op = 0

        while (ip < iEnd) {
            val i0 = `in`[ip++].toInt()
            val i1 = `in`[ip++].toInt()
            val i2 = if (ip < iEnd) `in`[ip++].toInt() else 'A'.toInt()
            val i3 = if (ip < iEnd) `in`[ip++].toInt() else 'A'.toInt()

            require(!(i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)) { "Illegal character in Base64 encoded data." }

            val b0 = map2[i0].toInt()
            val b1 = map2[i1].toInt()
            val b2 = map2[i2].toInt()
            val b3 = map2[i3].toInt()

            require(!(b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)) { "Illegal character in Base64 encoded data." }

            val o0 = b0 shl 2 or (b1 ushr 4)
            val o1 = b1 and 0xf shl 4 or (b2 ushr 2)
            val o2 = b2 and 3 shl 6 or b3

            out[op++] = o0.toByte()

            if (op < oLen) out[op++] = o1.toByte()
            if (op < oLen) out[op++] = o2.toByte()
        }
        return out
    }

    init {
        var i = 0
        run {
            var c = 'A'
            while (c <= 'Z') {
                map1[i++] = c
                c++
            }
        }
        run {
            var c = 'a'
            while (c <= 'z') {
                map1[i++] = c
                c++
            }
        }
        var c = '0'
        while (c <= '9') {
            map1[i++] = c
            c++
        }
        map1[i++] = '+'
        map1[i++] = '/'
    }

    init {
        Arrays.fill(map2, (-1).toByte())
        for (i in 0..63) {
            map2[map1[i].toInt()] = i.toByte()
        }
    }
}