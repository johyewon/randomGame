package com.hanix.randomgame.common.app

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.hanix.randomgame.common.constants.AppConstants

/**
 * 로그 통합 관리
 */
object GLog {
    /**
     * 현재 디버그 모드여부를 리턴
     * @param context
     * @return
     */
    @JvmStatic
    fun isDebuggable(context: Context): Boolean {
        val pm = context.packageManager
        try {
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            return 0 != appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
            /* debuggable variable will remain false */
        }
        return false
    }

    @Synchronized
    private fun dLong(theMsg: String, logType: Int) {
        var theMsg: String? = theMsg
        val MAX_INDEX = 2000
        if (theMsg == null) return
        if (theMsg.length > MAX_INDEX) {
            var theSubString = theMsg.substring(0, MAX_INDEX)
            theSubString = getWithMethodName(theSubString)
            when (logType) {
                1 -> Log.i(AppConstants.TAG, theSubString)
                2 -> Log.d(AppConstants.TAG, theSubString)
                3 -> Log.e(AppConstants.TAG, theSubString)
            }
            dLong(theMsg.substring(MAX_INDEX), MAX_INDEX)
        } else {
            theMsg = getWithMethodName(theMsg)
            when (logType) {
                1 -> Log.i(AppConstants.TAG, theMsg)
                2 -> Log.d(AppConstants.TAG, theMsg)
                3 -> Log.e(AppConstants.TAG, theMsg)
            }
        }
    }

    private fun getWithMethodName(log: String): String {
        return try {
            val ste = Thread.currentThread().stackTrace[5]
            "[" +
                    ste.fileName.replace(".java", "").replace(".kt", "") +
                    "::" +
                    ste.methodName +
                    "] " +
                    log
        } catch (e: Throwable) {
            e.printStackTrace()
            log
        }
    }

    @JvmStatic
    @Synchronized
    fun i(msg: String) {
        if (RandomApplication.getInstance().isDebuggable) dLong(msg, 1)
    }

    @JvmStatic
    @Synchronized
    fun d(msg: String) {
        if (RandomApplication.getInstance().isDebuggable) dLong(msg, 2)
    }

    @Synchronized
    fun e(msg: String) {
        if (RandomApplication.getInstance().isDebuggable) dLong(msg, 3)
    }

    @JvmStatic
    fun e(msg: String?, e: Exception?) {
        if (RandomApplication.getInstance().isDebuggable) Log.e(AppConstants.TAG, msg, e)
    }
}