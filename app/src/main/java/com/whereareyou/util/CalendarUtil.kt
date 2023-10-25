package com.whereareyou.util

import android.util.Log
import java.util.Calendar

object CalendarUtil {

    // year년 month월에 몇일까지 있는지 return
    private fun getDayOfMonth(year: Int, month: Int, flag: Type = Type.CURRENT): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        when (flag) {
            Type.PREVIOUS -> calendar.set(Calendar.MONTH, month - 1)
            Type.CURRENT -> calendar.set(Calendar.MONTH, month)
            Type.NEXT -> calendar.set(Calendar.MONTH, month + 1)
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    // year년 month월 date일이 무슨요일인지 return
    private fun getDayOfWeek(year: Int, month: Int, date: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, date)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    // year년 month월의 달력 정보를 return
    fun getCalendarInfo(year: Int, month: Int): ArrayList<String> {
        val calendar = Calendar.getInstance()

        // 첫 날 요일
        val firstDayOfWeek = getDayOfWeek(year, month, 1)

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, getDayOfMonth(year, month))
        val weekCount = calendar.get(Calendar.WEEK_OF_MONTH)

        Log.e("calendar", "$year, $month")
        Log.e("calendar", calendar.toString())

        calendar.set(Calendar.DATE, 2 - firstDayOfWeek)
        val arrList = ArrayList<String>()
        for (i in 1..(7 * weekCount)) {
            val str = "" + calendar.get(Calendar.YEAR) +
                    "/" + (calendar.get(Calendar.MONTH) + 1) +
                    "/" + calendar.get(Calendar.DATE)
            arrList.add(str)
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1)
        }
        Log.e("calendar", arrList.toString())
        return arrList
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