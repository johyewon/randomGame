package com.hanix.randomgame.view.event

import android.os.SystemClock
import android.view.View

abstract class OnSingleClickListener : View.OnClickListener {

    private var mLastClick: Long = 0
    abstract fun onSingleClick(v: View?)

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClick
        mLastClick = currentClickTime

        // 중복 클릭이 아닌 경우
        if (elapsedTime > MIN_CLICK_INTERVAL) {
            onSingleClick(v)
        }
    }

    companion object {
        private const val MIN_CLICK_INTERVAL: Long = 600
    }

}