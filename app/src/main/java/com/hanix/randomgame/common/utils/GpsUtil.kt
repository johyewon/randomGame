package com.hanix.randomgame.common.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.*

class GpsUtil {
    /**
     * GPS ->  주소
     *
     * @param latitude
     * @param longitude
     * @param activity
     * @return
     */
    fun getCurrentAddress(latitude: Double, longitude: Double, activity: Activity): String {

        //지오코더... GPS 를 주소로 변환
        val geocoder = Geocoder(activity.applicationContext, Locale.getDefault())
        val addresses: List<Address> = try {
            geocoder.getFromLocation(
                latitude,
                longitude,
                7
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(activity.applicationContext, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(activity.applicationContext, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }
        if (addresses.isEmpty()) {
            Toast.makeText(activity.applicationContext, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"
        }
        val address = addresses[0]
        return """
             ${address.getAddressLine(0)}
             
             """.trimIndent()
    }

    companion object {
        private const val GPS_ENABLE_REQUEST_CODE = 2001
        private const val PERMISSIONS_REQUEST_CODE = 100

        var REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        /**
         * 퍼미션 처리
         *
         * @param activity
         */
        fun checkRunTimePermission(activity: Activity) {

            //런타임 퍼미션 처리
            // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
            val hasFineLocationPermission = ContextCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION

            )
            val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

                // 2. 이미 퍼미션을 가지고 있다면
                // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)

                // 3.  위치 값을 가져올 수 있음
            } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

                // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, REQUIRED_PERMISSIONS[0])) {

                    // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                    Toast.makeText(
                        activity.applicationContext,
                        "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Toast.LENGTH_LONG
                    ).show()
                    // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult 에서 수신됩니다.
                } else {
                    // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                    // 요청 결과는 onRequestPermissionResult 에서 수신됩니다.

                }

                ActivityCompat.requestPermissions(
                    activity, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }

        /**
         * GPS 상태 확인
         *
         * @param activity
         * @return
         */
        fun checkGpsSts(activity: Activity): Boolean {
            var gpsEnable = false
            val manager = activity.getSystemService(Activity.LOCATION_SERVICE) as LocationManager
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gpsEnable = true
            }
            return gpsEnable
        }

        //여기부터는 GPS 활성화를 위한 메소드들
        fun showDialogForLocationServiceSetting(activity: Activity) {
            val builder = AlertDialog.Builder(activity.applicationContext)
            builder.setTitle("위치 서비스 비활성화")
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다. 위치 설정을 사용하시겠습니까??".trimIndent())
            builder.setCancelable(true)
            builder.setPositiveButton("설정") { _, _ ->
                val callGPSSettingIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                activity.startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
            }
            builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
            builder.create().show()
        }
    }
}