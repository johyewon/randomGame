package com.hanix.randomgame.common.app

import android.app.Activity
import android.os.Process
import androidx.multidex.MultiDexApplication
import com.hanix.randomgame.common.app.GLog.isDebuggable
import java.io.File
import java.util.*

/*
* 공유 Application
*/
class RandomApplication : MultiDexApplication() {
    //로그 표시를 위한 디버그 모드인지를 판별한다.
    var isDebuggable = false

    //앱 Crash 에러 로그가 저장된 경로(cache 디렉토리)
    private var logFilePathAppCrashError = ""

    //GLog(전체로그) 로그가 저장되는 파일이름(cache 디렉토리)
    var logFileFileNameGLog = ""

    //로그에서 사용되는 패키지명
    var logForPkgName = ""
    override fun onCreate() {
        super.onCreate()
        instance = this
        isDebuggable = isDebuggable(this)

        //앱 Crash 에러 로그가 저장된 경로(cache 디렉토리)
        logFilePathAppCrashError = File(this.cacheDir, "crash_log.txt").absolutePath
        //GLog(전체로그) 로그가 저장되는 파일이름(cache 디렉토리)
        logFileFileNameGLog = File(this.cacheDir, "payme_glog.txt").absolutePath
        logForPkgName = this.packageName
    }

    companion object {
        @JvmName("getInstance1")
        fun getInstance(): RandomApplication? {
            return instance
        }

        var instance: RandomApplication? = null
            private set
    }
}