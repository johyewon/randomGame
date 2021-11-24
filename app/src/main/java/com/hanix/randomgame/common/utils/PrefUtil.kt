package com.hanix.randomgame.common.utils

import android.content.Context
import android.content.SharedPreferences

object PrefUtil {

    private const val PREF_NAME = "Pref"
    private const val KEY_FCM_TOKEN_ID = "KEY_FCM_TOKEN_ID"

    private fun getEditor(context: Context): SharedPreferences.Editor {
        return getPref(context).edit()
    }

    private fun getPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    /*******  기본 SharedPreferences 시작  */
    fun setBoolean(context: Context, key: String?, value: Boolean) {
        val editor = getEditor(context)
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String?): Boolean {
        val pref = getPref(context)
        return pref.getBoolean(key, false)
    }

    fun setString(context: Context, key: String?, value: String?) {
        val editor = getEditor(context)
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String?): String? {
        val pref = getPref(context)
        return pref.getString(key, "")
    }

    fun setInt(context: Context, key: String?, value: Int) {
        val editor = getEditor(context)
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(context: Context, key: String?): Int {
        val pref = getPref(context)
        return pref.getInt(key, 0)
    }
    //*******  기본 SharedPreferences 끝 *******/
}