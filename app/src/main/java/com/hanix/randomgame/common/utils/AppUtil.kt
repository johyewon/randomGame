package com.hanix.randomgame.common.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import com.hanix.randomgame.common.app.GLog.e
import com.hanix.randomgame.common.constants.URLApi
import org.jsoup.Jsoup
import java.io.IOException

object AppUtil {
    /**
     * 앱이 실행 중인지 확인
     */
    fun isAppRunning(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val procInfos = activityManager.runningAppProcesses
        for (i in procInfos.indices) {
            if (procInfos[i].processName == context.packageName) {
                return true
            }
        }
        return false
    }

    /**
     * 확인하고 싶은 서비스가 실행중인지 확인
     */
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    /**
     * 현재 설치된 버전 가져오기
     *
     * @param context
     * @return
     */
    fun getAppVersion(context: Activity): String? {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e(e.message, e)
        }
        return null
    }

    /**
     * 플레이스토어 최신 버전 가져오기
     * - 주의사항 : 기기에 따라 다릅니다. 일 경우 읽어올 수 없음
     *
     * @return
     */
    private val marketVersion: String?
        get() {
            try {
                val doc = Jsoup.connect(URLApi.getStoreURL()).get()
                val currentVersionDiv = doc.select(".BgcNfc")
                val currentVersion = doc.select("div.hAyfc div span.htlgb")
                for (i in currentVersionDiv.indices) {
                    if (currentVersionDiv[i].text() == "Current Version") return currentVersion[i].text()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                e(e.message!!)
                e("문서 오류")
                e.printStackTrace()
            } catch (runTimeEx: RuntimeException) {
                e(runTimeEx.message!!)
                e("버전가져오기 에러")
            }
            return null
        }

    //  현재 설치 버전과 플레이스토어 버전이 같은지 확인
    fun isVersionEquals(context: Activity): Boolean {
        return marketVersion != null && marketVersion != "기기에 따라 다릅니다." && TextUtils.equals(
            getAppVersion(context), marketVersion
        )
    }
}