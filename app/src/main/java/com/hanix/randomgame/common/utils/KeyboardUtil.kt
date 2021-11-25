package com.hanix.randomgame.common.utils

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtil {

    fun showKeyboard(context: Context, editText: EditText) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }

    fun hideKeyboard(context: Context, binder: IBinder) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binder, 0)
    }

}