package com.hanix.randomgame.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;

import com.hanix.randomgame.R;
import com.hanix.randomgame.common.app.GLog;

import java.util.Objects;


public class DlgUtil {

    private static boolean mWaitingDlgIsShowing = false;

    private static AlertDialog mWaitingDialog;
    private static AlertDialog mMsgDialog;
    private static AlertDialog mConfirmDialog;

    public static void showWaitingDlg(final Context context) {

        //현재 waiting 보여지고 있는 상태면, 다시 show 요청이 들어와도 show 하지 않는다.
        //(waiting 다이얼로그만의 특성)
        if (mWaitingDlgIsShowing) return;

        mWaitingDlgIsShowing = true;

        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                GLog.d("showWaitingDlg() ... ...");

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(R.layout.view_dlg_loading);
                mWaitingDialog = builder.create();
                mWaitingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mWaitingDialog.setCancelable(false);
                Objects.requireNonNull(mWaitingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                mWaitingDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //10초 후에는 무조건 없앤다.
        new Handler(Looper.getMainLooper()).postDelayed(DlgUtil::hideWaitingDlg, 10000);
    }

    public static void hideWaitingDlg() {
        if (mWaitingDialog != null) {
            GLog.d("hideWaitingDlg().....");
            try {
                mWaitingDialog.dismiss();
                mWaitingDlgIsShowing = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 기본 알림 다이얼로그
     *
     * @param context
     * @param msg
     */
    public static void showMsgDlg(Context context, String msg) {

        destroyAllWindow();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);

        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                mMsgDialog = builder.create();
                mMsgDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 클릭 이벤트 있는 다이얼로그 (확인 버튼 하나)
     *
     * @param context
     * @param msg
     * @param isCancelable
     * @param okListener
     */
    public static void showConfirmDlg(Context context, String msg, boolean isCancelable, DialogInterface.OnClickListener okListener) {
        destroyAllWindow();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setCancelable(isCancelable);
        if (okListener == null) {
            builder.setPositiveButton(R.string.msg_ok, (dialog, which) -> mConfirmDialog.dismiss());
        } else {
            builder.setPositiveButton(R.string.msg_ok, okListener);
        }

        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                mConfirmDialog = builder.create();
                mConfirmDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
    public static void showConfirmDlg2(Context context, String msg, boolean isCancelable,
                                       String okBtnTitle, DialogInterface.OnClickListener okListener,
                                       String cancelBtnTitle, DialogInterface.OnClickListener cancelListener) {
        destroyAllWindow();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setCancelable(isCancelable);
        if (okListener == null) {
            builder.setPositiveButton(okBtnTitle, (dialog, which) -> mConfirmDialog.dismiss());
        } else {
            builder.setPositiveButton(okBtnTitle, okListener);
        }

        if (cancelListener == null) {
            builder.setNegativeButton(cancelBtnTitle, (dialog, which) -> mConfirmDialog.dismiss());
        } else {
            builder.setNegativeButton(cancelBtnTitle, cancelListener);
        }

        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                mConfirmDialog = builder.create();
                mConfirmDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 모든 다이얼로그 다 내리기
     */
    public static void destroyAllWindow() {
        mWaitingDlgIsShowing = false;
        if (mWaitingDialog != null) try {
            mWaitingDialog.dismiss();
        } catch (Exception ignored) { }
        if (mMsgDialog != null) try {
            mMsgDialog.dismiss();
        } catch (Exception ignored) { }
        if (mConfirmDialog != null) try {
            mConfirmDialog.dismiss();
        } catch (Exception ignored) { }
    }

}
