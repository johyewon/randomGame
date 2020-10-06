package com.hanix.randomgame.common.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * 통화 수신에 대한 처리
 */
public class PhoneCallUtil {

    public static boolean isCalling = false;


    /**
     * Activity 나 fragment 가 만들어졌을 때 부여한다.
     * @param context
     * @return
     */
    public static boolean isCallingListener(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        return isCalling;
    }

    /** 전화 수신 이벤트 : READ_PHONE_STATE 필요 **/
    public static PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE :
                    // 통화중이 아닐 때
                    isCalling = false;
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK :
                    // 통화중
                case TelephonyManager.CALL_STATE_RINGING :
                    // 전화 벨이 울릴 때
                    isCalling = true;
                    break;
            }
        }
    };
}
