package com.hanix.randomgame.common.utils

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

/**
 * 통화 수신에 대한 처리
 */
object PhoneCallUtil {
    var isCalling = false

    /**
     * Activity 나 fragment 가 만들어졌을 때 부여한다.
     * @param context
     * @return
     */
    fun isCallingListener(context: Context): Boolean {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        return isCalling
    }


    /** 전화 수신 이벤트 : READ_PHONE_STATE 필요  */
    var phoneStateListener: PhoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String) {
            when (state) {
                TelephonyManager.CALL_STATE_IDLE ->                     // 통화중이 아닐 때
                    isCalling = false
                TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING ->                     // 전화 벨이 울릴 때
                    isCalling = true
            }
        }
    }
}