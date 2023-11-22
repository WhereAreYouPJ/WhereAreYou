package com.whereareyou.util

import android.util.Log
import com.whereareyou.data.Schedule
import java.util.Calendar

object CalendarUtil {

    // year년 month월에 몇일까지 있는지 return
    fun getDayOfMonth(year: Int, month: Int, flag: Type = Type.CURRENT): Int {
        val calendar = Calendar.getInstance()
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

    // year년 month월의 달력 정보를 return
    fun getCalendarInfo(year: Int, month: Int): List<Schedule> {
        val calendar = Calendar.getInstance()

        // month월 첫 날 요일
        val firstDayOfWeek = getDayOfWeek(year, month, 1)

        // month월이 총 몇주인지 계산
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DATE, getDayOfMonth(year, month))
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

    // 오늘의 년월일 정보를 return
    fun getTodayInfo(): List<Int> {
        val calendar = Calendar.getInstance()
        return listOf(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE))
    }
}