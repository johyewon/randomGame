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
import android.os.Handler
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

object ToastUtil {
    private var mToast: Toast? = null

    /**
     * LENGTH_SHORT Toast
     *
     * @param context
     * @param msg
     */
    fun showToastS(context: Context?, msg: String?) {
        Handler(Looper.getMainLooper()).post {
            hideToast()
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            mToast.show()
        }
    }

    /**
     * LENGTH_LONG Toast
     *
     * @param context
     * @param msg
     */
    fun showToastL(context: Context?, msg: String?) {
        Handler(Looper.getMainLooper()).post {
            hideToast()
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            mToast.show()
        }
    }

    /**
     * 토스트 전부 내리기
     */
    fun hideToast() {
        if (mToast != null) {
            i("Toast hide true!")
            mToast!!.cancel()
        }
    }

    /**
     * @param context
     * @param msg
     * @param isHtml
     */
    fun showToastPresent(context: Context?, msg: String?, isHtml: Boolean) {
        Handler(Looper.getMainLooper()).post {
            if (isHtml) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Toast.makeText(
                        context,
                        Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    @kotlin.jvm.JvmStatic
    fun setToastLayout(activity: Activity, msg: String?, context: Context?) {
        val inflater = activity.layoutInflater
        val layout =
            inflater.inflate(R.layout.toast_layout, activity.findViewById(R.id.toastLayout))
        val toastTv = layout.findViewById<TextView>(R.id.toastTv)
        toastTv.text = msg
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}