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
import android.graphics.Point
import android.graphics.Rect
import android.media.MediaMetadataRetriever
import com.hanix.randomgame.common.utils.Utils.Useage
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
import android.widget.TextView
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
import android.view.*
import com.hanix.randomgame.common.receiver.StoreReceiver
import com.hanix.randomgame.common.constants.URLApi
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.InvocationTargetException
import java.text.DecimalFormat
import java.util.HashSet

object Utils {
    /**
     * 대문자로 변환
     * @param s
     * @return
     */
    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    /**
     * 문자 -> 숫자 (오류일 경우 -1 반환)
     * @param strInt
     * @return
     */
    fun getInt(strInt: String): Int {
        try {
            return strInt.trim { it <= ' ' }.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 문자열 배열 --> 버블소트 --> int 배열
     * @param strArr
     * @return
     */
    fun getBubbleSort(strArr: Array<String>): IntArray {
        val intArr = IntArray(strArr.size)

        //str -> int
        for (i in strArr.indices) {
            var naNum = 0
            try {
                naNum = strArr[i].trim { it <= ' ' }.toInt()
            } catch (ignored: Exception) {
            }
            intArr[i] = naNum
        }
        var temp: Int
        for (i in intArr.size downTo 1) {
            for (j in 0 until i - 1) {
                if (intArr[j] > intArr[j + 1]) {
                    temp = intArr[j]
                    intArr[j] = intArr[j + 1]
                    intArr[j + 1] = temp
                }
            }
        }
        return intArr
    }

    /**
     * 패키지명 -> app ID 반환
     * @param packageName
     * @return
     */
    @SuppressLint("NewApi")
    fun getPackageUid(context: Context, packageName: String?): Int {
        var uid = -1
        try {
            uid = context.packageManager.getPackageUid(packageName!!, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            //e.printStackTrace();
        }
        return uid
    }

    /**
     * 현재앱의 버젼을 반환한다. ex)1.0.0
     * @return 1.0.0
     */
    fun getAppVersionName(context: Context): String {
        var curAppVersion = ""
        try {
            curAppVersion =
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return curAppVersion
    }

    /**
     * List<Integer> -> int[] 변환
     * @param list
     * @return
    </Integer> */
    fun toIntArray(list: List<Int>): IntArray {
        val ret = IntArray(list.size)
        var i = 0
        for (e in list) ret[i++] = e
        return ret
    }

    /**
     * HashSet<Integer> -> int[] 변환
     * @param list
     * @return
    </Integer> */
    fun toIntArray(list: HashSet<Int>): IntArray {
        val ret = IntArray(list.size)
        var i = 0
        for (e in list) ret[i++] = e
        return ret
    }

    /**
     * 비디오의 Thumbnail 이미지를 Bitmap 으로 취득한다.
     * @param videoPath
     * @return
     * @throws Throwable
     */
    @Throws(Throwable::class)
    fun retrieveVideoFrameFromVideo(videoPath: String?): Bitmap? {
        val bitmap: Bitmap
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(videoPath)
            bitmap =
                mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Throwable("Exception in retrieveVideoFrameFromVideo(String videoPath)" + e.message)
        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
    }

    /**
     * KB. MB, GB, TB 단위변환
     *
     * @param size
     * @return
     */
    fun measureUnit(size: Int): Useage {
        val m = (size / 1024).toDouble()
        val g = (size / 1048576).toDouble()
        val t = (size / 1073741824).toDouble()
        val useage = Useage()
        val dec = DecimalFormat("0.0")
        if (size >= 0) {
            useage.unit = "KB"
            useage.useage = dec.format(size.toLong())
        }
        if (m > 0) {
            useage.unit = "MB"
            useage.useage = dec.format(m)
        }
        if (g > 0) {
            useage.unit = "GB"
            useage.useage = dec.format(g)
        }
        if (t > 0) {
            useage.unit = "TB"
            useage.useage = dec.format(t)
        }
        return useage
    }

    /** 앱이 사용가능한 스크린 사이즈 취득  */
    fun getAppUsableScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    /** 화면 전체 스크린 사이즈 취득  */
    fun getRealScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size)
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = (Display::class.java.getMethod("getRawWidth").invoke(display) as Int)
                size.y = (Display::class.java.getMethod("getRawHeight").invoke(display) as Int)
            } catch (ignored: IllegalAccessException) {
            } catch (ignored: InvocationTargetException) {
            } catch (ignored: NoSuchMethodException) {
            }
        }
        return size
    }

    /** 타이틀바 높이취득  */
    fun getTitleBarHeight(activity: Activity): Int {
        val rect = Rect()
        val win = activity.window
        win.decorView.getWindowVisibleDisplayFrame(rect)
        val statusBarHeight = rect.top
        val contentViewTop = win.findViewById<View>(Window.ID_ANDROID_CONTENT).top
        return contentViewTop - statusBarHeight
    }

    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4)
                    + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    fun byteArrayToHexString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02X", b and 0xff))
        }
        return sb.toString()
    }

    /**
     * 블럭 사이즈 내에서 부족한 바이트를 zero 패딩
     */
    fun addPadding(source: ByteArray, blockSize: Int): ByteArray? {
        val paddingCnt = source.size % blockSize
        var paddingResult: ByteArray? = null
        if (paddingCnt != 0) {
            paddingResult = ByteArray(source.size + (blockSize - paddingCnt))
            System.arraycopy(source, 0, paddingResult, 0, source.size)

            // 패딩 0x00 값을 추가한다.
            val addPaddingCnt = blockSize - paddingCnt
            for (i in 0 until addPaddingCnt) {
                paddingResult[source.size + i] = 0x00.toByte()
            }
        } else {
            paddingResult = source
        }
        return paddingResult
    }

    fun addPaddingStr(orgStr: String?, len: Int): String {
        var orgStr = orgStr
        if (orgStr != null) {
            var calcLen = orgStr.length - len
            return if (calcLen < 0) {
                calcLen = Math.abs(calcLen)
                val orgStrBuilder = StringBuilder(orgStr)
                for (i in 0 until calcLen) {
                    orgStrBuilder.append(" ")
                }
                orgStr = orgStrBuilder.toString()
                orgStr
            } else {
                orgStr.substring(0, len)
            }
        }
        return ""
    }

    class Useage {
        var useage: String? = null
        var unit: String? = null
    }
}