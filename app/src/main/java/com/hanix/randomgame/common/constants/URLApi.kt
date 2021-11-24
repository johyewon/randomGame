package com.hanix.randomgame.common.constants

import com.hanix.randomgame.common.app.RandomApplication

object URLApi {

    const val SERVER_URL = ""
    const val PLAY_STORE_REAL = AppConstants.STORE_URL_GOOGLE + "com.hanix.randomgame"
    const val PLAY_STORE_DEV = AppConstants.STORE_URL_GOOGLE + "com.hanix.randomgame"

    val storeURL: String
        get() = if (RandomApplication.getInstance()?.isDebuggable == true) {
            PLAY_STORE_DEV
        } else PLAY_STORE_REAL

    /** 서버 url  */ // url 취득
    val serverUrl: String
        get() = if (RandomApplication.getInstance()?.isDebuggable == true) {
            SERVER_URL
        } else ""
}