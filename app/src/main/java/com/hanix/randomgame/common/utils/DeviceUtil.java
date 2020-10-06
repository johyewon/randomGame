package com.hanix.randomgame.common.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.security.KeyChain;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.util.Locale;

public class DeviceUtil {

    public  enum DEVICE_MAKER {
        SAMSUNG, LGE, ETC
    }

    /**
     * 현재 기기의 모델명을 취득
     */
    public static String getDeviceModelName() { return Build.MODEL; }

    /**
     * 현재 App 의 build.gradle 의 VersionName 항목값을 가져온다
     */
    public static String getSwVerName(Context context) {
        String ver = "";
        try {
            ver = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ver;
    }

    /**
     * 현재 기기의 Binary version 을 취득
     */
    public static String getBinaryVersionName() {
        String rdoVer = Build.getRadioVersion();
        if(!TextUtils.isEmpty(rdoVer) && rdoVer.length() > 32) {
            rdoVer = rdoVer.substring(0,32);
        }
        return rdoVer;
    }

    /**
     * 삼성, 엘지 디바이스 체크
     */
    public static DEVICE_MAKER getDeviceMaker() {
        if(Build.MANUFACTURER.toUpperCase().contains("SAMSUNG")) {
            return DEVICE_MAKER.SAMSUNG;
        }
        else if( Build.MANUFACTURER.toUpperCase().contains("LGE")) {
            return DEVICE_MAKER.LGE;
        }
        return DEVICE_MAKER.ETC;
    }

    /**
     * 인터넷 접속 여부 체크
     */
    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 현재 Wifi 접속중인지를 검색한다.
     * @return true: wifi 접속중.  false: wifi 접속중 아님.
     */
    public static boolean isWifiConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network network = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                network = connectivityManager.getActiveNetwork();
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 현재 Cellular 접속중인지를 검색한다.
     * @return true: cellular 접속중.  false: Cellular 접속중 아님.
     */
    public static boolean isCellularConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network network = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                network = connectivityManager.getActiveNetwork();
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 유니크한 단말번호 - AndroidId 사용
     * - 대신 공장 초기화 시 사라질 수 있음
     * @param resolver
     * @return string
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(ContentResolver resolver) {
        return Settings.Secure.getString(resolver, Settings.Secure.ANDROID_ID);
    }

    /**
     * iOS 에서 키체인 쓰는 것과 비슷
     *  디바이스 고유값 가져오기
     *  - 안드로이드 아이디와 동일하게 공장 초기화시 사라짐
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getAndroidKeyChain() {
        return KeyChain.EXTRA_KEY_ALIAS;
    }

    /**
     * 국가 코드를 가져와준다
     * - ex) KR, US, UK ...
     * @param context
     * @return
     */
    public static String getAndroidCountry(Context context) {
        Locale locale;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale.getCountry();
    }

    /***
     * 기기가 태블릿인지 구분
     * @param context
     * @return
     */
    public static boolean IsTablet(Context context) {
        //화면 사이즈 종류 구하기
        int screenSizeType = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        return screenSizeType == Configuration.SCREENLAYOUT_SIZE_XLARGE || screenSizeType == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 기기가 휴대폰인지 구분
     * @param context
     * @return
     */
    public static boolean isPhone(Context context) {
        int screenSizeType = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        return screenSizeType == Configuration.SCREENLAYOUT_SIZE_SMALL || screenSizeType == Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }
}

