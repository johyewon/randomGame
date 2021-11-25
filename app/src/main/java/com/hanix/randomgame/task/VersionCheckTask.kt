package com.hanix.randomgame.task

import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import com.hanix.randomgame.R
import com.hanix.randomgame.common.app.GLog.d
import com.hanix.randomgame.common.app.GLog.e

/**
 * 앱을 사용 중일 때에도 업데이트가 될 수 있게  처리
 */
class VersionCheckTask(var context: Context, var activity: Activity) : InstallStateUpdatedListener {

    var appUpdateManager: AppUpdateManager? = null
    var appUpdateInfoTask: Task<AppUpdateInfo>? = null

    init {
        init()
    }

    private fun init() {
        appUpdateManager = AppUpdateManagerFactory.create(context)
        appUpdateManager?.registerListener(this)

        appUpdateInfoTask = appUpdateManager?.appUpdateInfo

        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                startUpdate(appUpdateInfo)

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate()

            } else {
                e("checkForAppUpdateAvailability : something else " + appUpdateInfo.installStatus() + " and updateAvailable is " + appUpdateInfo.updateAvailability())
            }
        }
    }

    private fun startUpdate(appUpdateInfo: AppUpdateInfo?) {
        try {
            if (appUpdateManager != null && appUpdateInfo != null) {
                appUpdateManager!!.startUpdateFlowForResult(
                    appUpdateInfo, AppUpdateType.IMMEDIATE, activity, MY_REQUEST_CODE
                )
            }
        } catch (ignored: SendIntentException) {
        }
    }

    private fun popupSnackBarForCompleteUpdate() {
        val snackbar = Snackbar.make(
            activity.window.decorView.rootView,
            activity.getString(R.string.txt_msg_you_must_update),
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(activity.getString(R.string.install)) { if (appUpdateManager != null) appUpdateManager!!.completeUpdate() }
        snackbar.setAction(activity.getString(R.string.cmm_cancel)) { snackbar.dismiss() }
        snackbar.show()
    }

    fun updateResult(resultCode: Int, requestCode: Int) {
        if (requestCode == MY_REQUEST_CODE && resultCode != ActivityResult.RESULT_IN_APP_UPDATE_FAILED) e(
            "RESULT_IN_APP_UPDATE_FAILED : $resultCode"
        )
    }


    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {        // 업데이트 항목 다운로드 완료
            popupSnackBarForCompleteUpdate()
        } else if (state.installStatus() == InstallStatus.INSTALLED) {      // 설치 완료
            if (appUpdateManager != null) appUpdateManager!!.unregisterListener(this)
        } else {        // 업데이트 필요 없음
            d("installStateUpdateListener  : state : " + state.installStatus())
        }
    }

    companion object {
        const val MY_REQUEST_CODE = 111
    }
}
