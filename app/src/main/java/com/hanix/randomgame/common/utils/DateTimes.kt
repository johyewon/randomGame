package com.hanix.randomgame.common.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import com.hanix.randomgame.common.app.GLog
import com.hanix.randomgame.common.app.GLog.i
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object DateTimes {
    private val DF_YYYY_MM_DD = SimpleDateFormat("yyyy-MM-dd")
    private val DF_YYYYMMDDHHMMSS = SimpleDateFormat("yyyyMMddHHmmss")

    /**
     *
     * @param inForm    입력 형식(ex : yyyy-MM-dd)
     * @param outForm   출력 형식(ex : yyyy.MM.dd)
     * @param inDate    입력일자
     * @return          출력일자
     */
    fun parseDate(inForm: String?, outForm: String?, inDate: String?): String? {
        val sf = SimpleDateFormat(inForm)
        val sf2 = SimpleDateFormat(outForm)

        val date: Date
        var ret: String?

        try {
            date = sf.parse(inDate)
            ret = sf2.format(date)
        } catch (e: Exception) {
            ret = inDate
        }

        return ret
    }

    /**
     * 입력받은 일자가 현재 일자보다 이전인지 여부를 확인
     * @param date 만료일자(YYYY-MM-DD)
     * @return true : 지남
     */
    fun remainDay(date: String?): Int {
        var ret = -1

        if (date == null || date.length > 10) {
            return ret
        }

        ret =
            try {
                val sf2 = SimpleDateFormat(date)
                val form1 = DF_YYYY_MM_DD.format(Date()) // 오늘일자
                val form2 = sf2.format(Date())
                val diff = DF_YYYY_MM_DD.parse(form2).time - DF_YYYY_MM_DD.parse(form1).time
                (diff / 1000 / 60 / 60 / 24).toInt()
            } catch (e: Exception) {
                -1
            }

        return ret
    }

    /**
     * 1 minute = 60 seconds
     * 1 hour = 60 x 60 = 3600
     * 1 day = 3600 x 24 = 86400
     *
     * SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hhmmss")
     * Date startDate = simpleDateFormat.parse("2019-04-02 173010")
     * Date endDate = simpleDateFormat.parse("2019-04-02 240000")
     *
     * ex)printDifference(startDate, endDate)
     */
    fun printDifference(startDate: Date, endDate: Date) {

        //milliseconds
        var different = endDate.time - startDate.time

        GLog.d("startDate : $startDate")
        GLog.d("endDate : $endDate")
        GLog.d("different : $different")

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different %= daysInMilli

        val elapsedHours = different / hoursInMilli
        different %= hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli

        val elapsedSeconds = different / secondsInMilli

        GLog.d("$elapsedDays days, $elapsedHours hours, $elapsedMinutes minutes, $elapsedSeconds seconds%n")
    }

    /**
     * 현재날짜 기준으로 startDate 와 endDate 가 유효한 데이터 기간안에 있는지를 확인한다.
     *
     * @param fromDateStr ex) 20190322000000 (YYYYMMDDHHMMSS)
     * @param toDateStr ex) 20190625235959 (YYYYMMDDHHMMSS)
     * @return true: 기간안에 있어 실행가능함.  false: 기간안에 없어 실행 불가능함.
     */
    fun getIsExecutableDate(fromDateStr: String?, toDateStr: String?): Boolean {
        var rtnBool = false
        if (TextUtils.isEmpty(fromDateStr) || TextUtils.isEmpty(toDateStr)) {
            return false
        }

        try {
            var diffMilli: Long
            val todayStr = DF_YYYYMMDDHHMMSS.format(Date())
            val todayDt = DF_YYYYMMDDHHMMSS.parse(todayStr)
            val fromDt = DF_YYYYMMDDHHMMSS.parse(fromDateStr)
            val toDt = DF_YYYYMMDDHHMMSS.parse(toDateStr)

            //1.fromDate 는 오늘 날짜보다 작거나 같아야 한다.
            diffMilli = todayDt.time - fromDt.time
            if (diffMilli >= 0)
                rtnBool = true

            //2.toDate 는 오늘 날짜보다 크커나 같아야 한다.
            diffMilli = toDt.time - todayDt.time
            if (diffMilli >= 0)
                rtnBool = true

        } catch (e: Exception) {
            e.printStackTrace()
            rtnBool = false
        }
        return rtnBool
    }

    /**
     *
     * @param format    출력일자 폼 (yyyy-MM-dd)
     * @param offset    금일기준 옵셋값(-1:하루전 0:금일 1:내일)
     * @return
     */
    fun moveDate(format: String?, offset: Int): String {
        var format = format

        if (format == null) {
            format = "yyyy.MM.dd"
        }

        val sdf = SimpleDateFormat(format)
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.DATE, offset)

        return sdf.format(cal.time)
    }

    /**
     * 파라미터로 넘겨준 날짜의 하루가 끝나는 시간을 넘겨준다.
     * @param date
     * @return
     */
    fun atEndOfDay(date: Date?): Date {
        val calendar = Calendar.getInstance()

        calendar.time = date
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999

        return calendar.time
    }

    /**
     * 파라미터로 넘겨준 날짜의 하루가 시작하는 시간을 넘겨준다.
     * @param date
     * @return
     */
    fun atStartOfDay(date: Date?): Date {
        val calendar = Calendar.getInstance()

        calendar.time = date
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        return calendar.time
    }

    /**
     * 주어진 milliseconds 의 남은 시간을 표시한다.
     */
    fun printRemainTime(remainMilli: Long): String {
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = remainMilli / daysInMilli
        val diffMilli11 = remainMilli % daysInMilli
        val elapsedHours = diffMilli11 / hoursInMilli
        val diffMilli22 = diffMilli11 % hoursInMilli
        val elapsedMinutes = diffMilli22 / minutesInMilli
        val diffMilli33 = diffMilli22 % minutesInMilli
        val elapsedSeconds = diffMilli33 / secondsInMilli

        val msg = (elapsedDays.toString() + " days, " + elapsedHours + " hours, "
                + elapsedMinutes + " minutes, " + elapsedSeconds + " seconds")
        i("다음작업 남은시간 ===> $msg")

        return msg
    }
}