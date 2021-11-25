package com.hanix.randomgame.common.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.Gravity
import android.widget.Toast
import com.hanix.randomgame.R
import com.hanix.randomgame.common.app.GLog.i

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
            mToast?.show()
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
            mToast?.show()
        }
    }

    /**
     * 토스트 전부 내리기
     */
    private fun hideToast() {
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

    /**
     * toast with layout
     */
    @JvmStatic
    fun setToastLayout(activity: Activity, msg: String?, context: Context?) {
        val inflater = activity.layoutInflater
        val layout =
            inflater.inflate(R.layout.toast_layout, activity.findViewById(R.id.toastLayout))

        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        toast.show()
    }
}