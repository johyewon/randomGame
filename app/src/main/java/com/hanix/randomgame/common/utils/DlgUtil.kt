package com.hanix.randomgame.common.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Window
import com.hanix.randomgame.R
import com.hanix.randomgame.common.app.GLog.d

object DlgUtil {

    private var mWaitingDlgIsShowing = false
    private var mWaitingDialog: AlertDialog? = null
    private var mMsgDialog: AlertDialog? = null
    private var mConfirmDialog: AlertDialog? = null

    fun showWaitingDlg(context: Context?) {

        //현재 waiting 보여지고 있는 상태면, 다시 show 요청이 들어와도 show 하지 않는다.
        //(waiting 다이얼로그만의 특성)
        if (mWaitingDlgIsShowing) return

        mWaitingDlgIsShowing = true

        Handler(Looper.getMainLooper()).post {
            try {
                d("showWaitingDlg() ... ...")

                val builder = AlertDialog.Builder(context)
                builder.setView(R.layout.view_dlg_loading)
                mWaitingDialog = builder.create()

                mWaitingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mWaitingDialog?.setCancelable(false)
                mWaitingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                mWaitingDialog?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //10초 후에는 무조건 없앤다.
        Handler(Looper.getMainLooper()).postDelayed({ hideWaitingDlg() }, 10000)
    }

    private fun hideWaitingDlg() {
        if (mWaitingDialog != null) {
            d("hideWaitingDlg().....")

            try {
                mWaitingDialog!!.dismiss()
                mWaitingDlgIsShowing = false
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 기본 알림 다이얼로그
     *
     * @param context
     * @param msg
     */
    fun showMsgDlg(context: Context?, msg: String?) {
        destroyAllWindow()

        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)

        Handler(Looper.getMainLooper()).post {
            try {
                mMsgDialog = builder.create()
                mMsgDialog?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 클릭 이벤트 있는 다이얼로그 (확인 버튼 하나)
     *
     * @param context
     * @param msg
     * @param isCancelable
     * @param okListener
     */
    fun showConfirmDlg(
        context: Context?,
        msg: String?,
        isCancelable: Boolean,
        okListener: DialogInterface.OnClickListener?
    ) {
        destroyAllWindow()

        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
        builder.setCancelable(isCancelable)

        if (okListener == null) {
            builder.setPositiveButton(R.string.msg_ok) { _, _ -> }
        } else {
            builder.setPositiveButton(R.string.msg_ok, okListener)
        }

        Handler(Looper.getMainLooper()).post {
            try {
                mConfirmDialog = builder.create()
                mConfirmDialog?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 클릭 이벤트 있는 다이얼로그 (확인, 취소)
     *
     * @param context
     * @param msg
     * @param isCancelable
     * @param okBtnTitle
     * @param okListener
     * @param cancelBtnTitle
     * @param cancelListener
     */
    fun showConfirmDlgWithNegative(
        context: Context?, msg: String?, isCancelable: Boolean,
        okBtnTitle: String?, okListener: DialogInterface.OnClickListener?,
        cancelBtnTitle: String?, cancelListener: DialogInterface.OnClickListener?
    ) {
        destroyAllWindow()

        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
        builder.setCancelable(isCancelable)

        if (okListener == null) {
            builder.setPositiveButton(okBtnTitle) { _, _ -> }
        } else {
            builder.setPositiveButton(okBtnTitle, okListener)
        }
        if (cancelListener == null) {
            builder.setNegativeButton(cancelBtnTitle) { _, _ -> }
        } else {
            builder.setNegativeButton(cancelBtnTitle, cancelListener)
        }
        Handler(Looper.getMainLooper()).post {
            try {
                mConfirmDialog = builder.create()
                mConfirmDialog?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 모든 다이얼로그 다 내리기
     */
    private fun destroyAllWindow() {
        mWaitingDlgIsShowing = false
        try {
            if (mWaitingDialog != null)
                mWaitingDialog!!.dismiss()

            if (mMsgDialog != null)
                mMsgDialog!!.dismiss()

            if (mConfirmDialog != null)
                mConfirmDialog!!.dismiss()
        } catch (ignored: Exception) {
        }
    }
}