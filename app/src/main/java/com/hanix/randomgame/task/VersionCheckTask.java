package com.hanix.randomgame.task;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.ActivityResult;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.hanix.randomgame.R;
import com.hanix.randomgame.common.app.GLog;

/**
 * 앱을 사용 중일 때에도 업데이트가 될 수 있게  처리
 */
public class VersionCheckTask {

    Context context;
    Activity activity;

    public static final int MY_REQUEST_CODE = 111;
    AppUpdateManager appUpdateManager;
    Task<AppUpdateInfo> appUpdateInfoTask;

    public VersionCheckTask(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        init();
    }

    private void init() {
        appUpdateManager = AppUpdateManagerFactory.create(context);
        appUpdateManager.registerListener(listener);

        appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdate(appUpdateInfo);
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else {
                GLog.e("checkForAppUpdateAvailability : something else " + appUpdateInfo.installStatus() + " and updateAvailable is " + appUpdateInfo.updateAvailability());
            }
        });
    }

    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                if (appUpdateManager != null)
                    appUpdateManager.unregisterListener(listener);
            } else {
                GLog.d("installStateUpdateListener  : state : " + state.installStatus());
            }
        }
    };

    private void startUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            if (appUpdateManager != null && appUpdateInfo != null) {
                appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo
                        , AppUpdateType.IMMEDIATE
                        , activity
                        , MY_REQUEST_CODE
                );
            }
        } catch (IntentSender.SendIntentException ignored) {
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView().getRootView(), activity.getString(R.string.txt_msg_you_must_update), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(activity.getString(R.string.install), (v) -> {
            if (appUpdateManager != null)
                appUpdateManager.completeUpdate();
        });
        snackbar.setAction(activity.getString(R.string.cmm_cancel), (v) -> snackbar.dismiss());
        snackbar.show();
    }

    public void updateResult(int resultCode, int requestCode) {
        if (requestCode == MY_REQUEST_CODE && resultCode != ActivityResult.RESULT_IN_APP_UPDATE_FAILED)
            GLog.e("RESULT_IN_APP_UPDATE_FAILED : " + resultCode);
    }

}
