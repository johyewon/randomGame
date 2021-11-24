package com.hanix.randomgame.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.hanix.randomgame.common.app.GLog;

public class SnackBarUtil {

    private static Snackbar mSnackbar;

    public static void hideSnackbar() {
        if (mSnackbar != null) {
            GLog.i("Snackbar hide true!");
            mSnackbar.dismiss();
        }
    }

    public static void showSnackbar(final View view, final String msg, final int length) {
        new Handler(Looper.getMainLooper()).post(() -> {
            hideSnackbar();
            mSnackbar = Snackbar.make(view, msg, length);
            mSnackbar.show();
        });
    }

    public static void showSnackbarClick(final View view, final String msg, final int length, String clickMsg, View.OnClickListener clickListener) {
        hideSnackbar();

        new Handler(Looper.getMainLooper()).post(() -> {
            hideSnackbar();
            if (clickListener == null) {
                mSnackbar = Snackbar.make(view, msg, length).setAction(clickMsg, (v) -> mSnackbar.dismiss());
            } else {
                mSnackbar = Snackbar.make(view, msg, length).setAction(clickMsg, clickListener);
            }
            mSnackbar.show();
        });
    }
}
