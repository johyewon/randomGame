package com.hanix.randomgame.common.utils

import com.hanix.randomgame.common.app.GLog.isDebuggable
import com.hanix.randomgame.common.app.GLog.d
import com.hanix.randomgame.common.app.GLog.i
import com.hanix.randomgame.common.app.GLog.e
import androidx.multidex.MultiDexApplication
import com.hanix.randomgame.common.app.RandomApplication
import com.hanix.randomgame.common.app.GLog
import android.app.Activity
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import kotlin.Throws
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.hanix.randomgame.common.utils.Utils.Useage
import android.view.WindowManager
import android.view.Display
import android.os.Build
import com.hanix.randomgame.common.utils.DlgUtil
import android.os.Looper
import com.hanix.randomgame.R
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import com.hanix.randomgame.common.utils.GpsUtil
import android.location.LocationManager
import android.content.Intent
import com.hanix.randomgame.common.utils.FileUtil
import android.content.SharedPreferences
import com.hanix.randomgame.common.utils.PrefUtil
import com.hanix.randomgame.common.utils.UrlUtils
import com.hanix.randomgame.common.utils.DateTimes
import android.media.AudioManager
import com.hanix.randomgame.common.utils.ToastUtil
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.view.Gravity
import com.hanix.randomgame.common.utils.DeviceUtil.DEVICE_MAKER
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkCapabilities
import android.content.ContentResolver
import androidx.annotation.RequiresApi
import android.security.KeyChain
import kotlin.jvm.JvmOverloads
import com.hanix.randomgame.common.utils.Base64Coder
import com.hanix.randomgame.common.utils.SecretUtils
import org.bouncycastle.jce.provider.BouncyCastleProvider
import com.hanix.randomgame.common.utils.RealPathUtil
import android.provider.DocumentsContract
import android.content.ContentUris
import android.provider.MediaStore
import com.google.android.material.snackbar.Snackbar
import com.hanix.randomgame.common.utils.SnackBarUtil
import android.telephony.TelephonyManager
import com.hanix.randomgame.common.utils.PhoneCallUtil
import android.telephony.PhoneStateListener
import android.content.BroadcastReceiver
import com.hanix.randomgame.common.receiver.StoreReceiver
import com.hanix.randomgame.common.constants.URLApi
import java.io.UnsupportedEncodingException
import java.lang.StringBuilder
import java.lang.UnsupportedOperationException
import java.net.URLEncoder
import java.util.ArrayList

object UrlUtils {
    fun getUrlEncodeUTF8(s: String?): String {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw UnsupportedOperationException(e)
        }
    }

    /**
     * Map 을 url query 형태로 변환
     * ex) p3=a+%26+b&p2=cat&p1=12
     */
    fun getMap2UrlEncodeUTF8(map: Map<*, *>): String {
        val sb = StringBuilder()
        for ((key, value) in map) {
            if (value == null) continue
            if (sb.length > 0) {
                sb.append("&")
            }
            sb.append(
                String.format(
                    "%s=%s",
                    getUrlEncodeUTF8(key.toString()),
                    getUrlEncodeUTF8(value.toString())
                )
            )
        }
        return sb.toString()
    }

    /**
     * list 를 url 파라미터 형식으로 변형
     * @param list
     * @param key
     * @return
     */
    fun getList2UrlEncodeUTF8(list: ArrayList<String?>, key: String?): String {
        val sb = StringBuilder()
        sb.append(key).append("=")
        for (temp in list) {
            sb.append(getUrlEncodeUTF8(temp)).append(",")
        }
        var result = sb.toString()
        result = result.substring(0, result.length - 1)
        return result
    }
}