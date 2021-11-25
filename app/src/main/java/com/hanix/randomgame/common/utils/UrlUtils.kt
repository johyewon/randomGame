package com.hanix.randomgame.common.utils

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

object UrlUtils {

    private fun getUrlEncodeUTF8(s: String?): String {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw UnsupportedOperationException(e)
        }
    }

    /**
     * Map 을 url query 형태로 변환
     * ex) p3=a+%26+b&p2=cat&p1=12
     */
    fun getMap2UrlEncodeUTF8(map: Map<*, *>): String {
        val sb = StringBuilder()

        for ((key, value) in map) {
            if (value == null) continue
            if (sb.isNotEmpty()) {
                sb.append("&")
            }

            sb.append(
                String.format(
                    "%s=%s",
                    getUrlEncodeUTF8(key.toString()),
                    getUrlEncodeUTF8(value.toString())
                )
            )
        }

        return sb.toString()
    }

    /**
     * list 를 url 파라미터 형식으로 변형
     * @param list
     * @param key
     * @return
     */
    fun getList2UrlEncodeUTF8(list: ArrayList<String?>, key: String?): String {
        val sb = StringBuilder()
        sb.append(key).append("=")

        for (temp in list) {
            sb.append(getUrlEncodeUTF8(temp)).append(",")
        }

        var result = sb.toString()
        result = result.substring(0, result.length - 1)

        return result
    }
}