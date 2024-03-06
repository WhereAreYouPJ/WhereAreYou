package com.whereareyounow.util

import com.whereareyounow.data.calendar.Schedule
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

object CalendarUtil {

    // year년 month월에 몇일까지 있는지 return
    fun getDayOfMonth(year: Int, month: Int, flag: Type = Type.CURRENT): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, 1)
        calendar.set(Calendar.YEAR, year)
        when (flag) {
            Type.PREVIOUS -> calendar.set(Calendar.MONTH, month - 2)
            Type.CURRENT -> calendar.set(Calendar.MONTH, month - 1)
            Type.NEXT -> calendar.set(Calendar.MONTH, month)
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    // year년 month월 date일이 무슨요일인지 return
    // 일, 월, 화, 수, 목, 금, 토 차례로 1, 2, 3, 4, 5, 6, 7
    fun getDayOfWeek(year: Int, month: Int, date: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DATE, date)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun getDayOfWeekString(year: Int, month: Int, date: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DATE, date)
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            else -> "토"
        }
    }

    // year년 month월의 달력 정보를 return
    fun getCalendarInfo(year: Int, month: Int): List<Schedule> {
        val calendar = Calendar.getInstance()
        // month월 첫 날 요일
        val firstDayOfWeek = getDayOfWeek(year, month, 1)
        calendar.set(year, month - 1, 1)
        calendar.set(Calendar.DATE, getDayOfMonth(year, month))
        // month월이 총 몇주인지 계산
        val weekCount = calendar.get(Calendar.WEEK_OF_MONTH)

        calendar.set(Calendar.DATE, 1 - firstDayOfWeek)
        val scheduleList = List<Schedule>(7 * weekCount) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1)
            Schedule(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE))
        }
//        val arrList = ArrayList<String>()
//        for (i in 1..(7 * weekCount)) {
//            val str = "" + calendar.get(Calendar.YEAR) +
//                    "/" + (calendar.get(Calendar.MONTH) + 1) +
//                    "/" + calendar.get(Calendar.DATE)
//            arrList.add(str)
//            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1)
//        }
        return scheduleList
    }

    enum class Type {
        PREVIOUS, CURRENT, NEXT
    }

    // 현재 년으로부터 +- 100년의 정보를 리스트로 return
    fun getYearList(): ArrayList<Int> {
        val arrList = ArrayList<Int>()
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        for (i in currentYear - 100..currentYear + 100) arrList.add(i)

        return arrList
    }

    // 오늘의 년, 월, 일, 요일 정보를 return
    fun getTodayInfo(): List<Int> {
        val calendar = Calendar.getInstance()
        return listOf(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), calendar.get(Calendar.DAY_OF_WEEK))
    }

    fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)

    fun getCurrentMonth(): Int = Calendar.getInstance().get(Calendar.MONTH) + 1

    fun getCurrentDate(): Int = Calendar.getInstance().get(Calendar.DATE)

    // yyyy-MM-dd'T'HH:mm:ss 형식의 문자열을 Calendar로 변환한다
    fun getCalendarFromString(string: String): Calendar {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = format.parse(string)
        return  Calendar.getInstance().apply { time = date }
    }

    fun getMinuteDiffWithCurrentTime(time: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val t = dateFormat.parse(time)
        val currentDate = Date()
        val diffInMillis = t.time - currentDate.time
        val minutes = diffInMillis / (1000 * 60)
        return minutes.toInt().absoluteValue
    }
}