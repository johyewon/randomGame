package com.hanix.randomgame.common.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hanix.randomgame.common.app.GLog.i

object SnackBarUtil {
    private var mSnackbar: Snackbar? = null

    /**
     * 보여지고 있는 Snackbar 다 내리기
     */
    private fun hideSnackbar() {
        if (mSnackbar != null) {
            i("Snackbar hide true!")
            mSnackbar!!.dismiss()
        }
    }

    /**
     * 일반 Snackbar 노출
     */
    fun showSnackbar(view: View?, msg: String, length: Int) {
        if (view != null) {
            Handler(Looper.getMainLooper()).post {
                hideSnackbar()
                mSnackbar = Snackbar.make(view, msg, length)
                mSnackbar!!.show()
            }
        }
    }

    fun showSnackbarClick(
        view: View?,
        msg: String?,
        length: Int,
        clickMsg: String?,
        clickListener: View.OnClickListener?
    ) {
        hideSnackbar()
        Handler(Looper.getMainLooper()).post {
            hideSnackbar()
            mSnackbar = if (clickListener == null) {
                Snackbar.make(view!!, msg!!, length).setAction(clickMsg) { }
            } else {
                Snackbar.make(view!!, msg!!, length).setAction(clickMsg, clickListener)
            }
            mSnackbar!!.show()
        }
    }
}