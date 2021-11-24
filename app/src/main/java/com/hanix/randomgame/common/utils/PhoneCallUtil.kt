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
import android.content.*
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
import com.hanix.randomgame.common.utils.FileUtil
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
import androidx.annotation.RequiresApi
import android.security.KeyChain
import kotlin.jvm.JvmOverloads
import com.hanix.randomgame.common.utils.Base64Coder
import com.hanix.randomgame.common.utils.SecretUtils
import org.bouncycastle.jce.provider.BouncyCastleProvider
import com.hanix.randomgame.common.utils.RealPathUtil
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.google.android.material.snackbar.Snackbar
import com.hanix.randomgame.common.utils.SnackBarUtil
import android.telephony.TelephonyManager
import com.hanix.randomgame.common.utils.PhoneCallUtil
import android.telephony.PhoneStateListener
import com.hanix.randomgame.common.receiver.StoreReceiver
import com.hanix.randomgame.common.constants.URLApi

/**
 * 통화 수신에 대한 처리
 */
object PhoneCallUtil {
    var isCalling = false

    /**
     * Activity 나 fragment 가 만들어졌을 때 부여한다.
     * @param context
     * @return
     */
    fun isCallingListener(context: Context): Boolean {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        return isCalling
    }

    /** 전화 수신 이벤트 : READ_PHONE_STATE 필요  */
    var phoneStateListener: PhoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String) {
            when (state) {
                TelephonyManager.CALL_STATE_IDLE ->                     // 통화중이 아닐 때
                    isCalling = false
                TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING ->                     // 전화 벨이 울릴 때
                    isCalling = true
            }
        }
    }
}