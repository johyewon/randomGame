package com.hanix.randomgame.common.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.security.KeyChain
import android.text.TextUtils
import androidx.annotation.RequiresApi
import java.util.*

object DeviceUtil {
    /**
     * 현재 기기의 모델명을 취득
     */
    val deviceModelName: String
        get() = Build.MODEL

    /**
     * 현재 App 의 build.gradle 의 VersionName 항목값을 가져온다
     */
    fun getSwVerName(context: Context): String {
        var ver = ""

        try {
            ver = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ver
    }

    /**
     * 현재 기기의 Binary version 을 취득
     */
    val binaryVersionName: String
        get() {
            var rdoVer = Build.getRadioVersion()

            if (!TextUtils.isEmpty(rdoVer) && rdoVer.length > 32) {
                rdoVer = rdoVer.substring(0, 32)
            }

            return rdoVer
        }

    /**
     * 삼성, 엘지 디바이스 체크
     */
    val deviceMaker: DEVICE_MAKER
        get() {
            if (Build.MANUFACTURER.toUpperCase(Locale.ROOT).contains("SAMSUNG"))
                return DEVICE_MAKER.SAMSUNG
            else if (Build.MANUFACTURER.toUpperCase(Locale.ROOT).contains("LGE"))
                return DEVICE_MAKER.LGE

            return DEVICE_MAKER.ETC
        }

    /**
     * 인터넷 접속 여부 체크
     */
    @SuppressLint("MissingPermission")
    fun isOnline(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 현재 Wifi 접속중인지를 검색한다.
     * @return true: wifi 접속중.  false: wifi 접속중 아님.
     */
    @SuppressLint("MissingPermission")
    fun isWifiConnected(context: Context): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network: Network? = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 현재 Cellular 접속중인지를 검색한다.
     * @return true: cellular 접속중.  false: Cellular 접속중 아님.
     */
    @SuppressLint("MissingPermission")
    fun isCellularConnected(context: Context): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network: Network? = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 유니크한 단말번호 - AndroidId 사용
     * - 대신 공장 초기화 시 사라질 수 있음
     * @param resolver
     * @return string
     */
    @SuppressLint("HardwareIds")
    fun getAndroidId(resolver: ContentResolver?): String {
        return Settings.Secure.getString(resolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * iOS 에서 키체인 쓰는 것과 비슷
     * 디바이스 고유값 가져오기
     * - 안드로이드 아이디와 동일하게 공장 초기화시 사라짐
     * @return
     */
    @get:RequiresApi(api = Build.VERSION_CODES.O)
    val androidKeyChain: String
        get() = KeyChain.EXTRA_KEY_ALIAS

    /**
     * 국가 코드를 가져와준다
     * - ex) KR, US, UK ...
     * @param context
     * @return
     */
    fun getAndroidCountry(context: Context): String {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
        return locale.country
    }

    /***
     * 기기가 태블릿인지 구분
     * @param context
     * @return
     */
    fun isTablet(context: Context): Boolean {
        //화면 사이즈 종류 구하기
        val screenSizeType =
            context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        return screenSizeType == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSizeType == Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    /**
     * 기기가 휴대폰인지 구분
     * @param context
     * @return
     */
    fun isPhone(context: Context): Boolean {
        val screenSizeType =
            context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        return screenSizeType == Configuration.SCREENLAYOUT_SIZE_SMALL || screenSizeType == Configuration.SCREENLAYOUT_SIZE_NORMAL
    }

    enum class DEVICE_MAKER {
        SAMSUNG, LGE, ETC
    }
}