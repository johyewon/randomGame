package com.hanix.randomgame.view.event

import android.app.Activity
import com.hanix.randomgame.R
import com.hanix.randomgame.common.utils.ToastUtil.setToastLayout

/**
 * 뒤로가기(물리키) 터치 시 이벤트 처리
 */
class BackPressCloseHandler(private val activity: Activity) {

    private var backKeyPressedTime: Long = 0

    /**
     * 단순하게 activity 를 끄기만 할 때 사용함
     */
    fun onBackPressedInActivity() {
        activity.finish()
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    /**
     * mainActivity 등 주요 화면에서 물리키 작동 시
     */
    private fun onBackPress() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            setToastLayout(activity, "'뒤로' 버튼을 한 번 더 누르시면 종료됩니다.", activity.applicationContext)
            return
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) activity.finish()
    }
}