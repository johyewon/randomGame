package com.hanix.randomgame.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 사용자가 웹 화면 -> 플레이스토어에서 설치할 때 데이터 받아오기
 * referrer 추가해 플레이스토어에 링크 넘김 -> 이후 설치 시 receiver 들어옴
 */
class StoreReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val referrer = intent.getStringExtra("referrer")

        if (referrer != null && referrer.isNotEmpty()) {
            Companion.referrer = referrer
        }
    }

    companion object {
        var referrer = ""
    }
}