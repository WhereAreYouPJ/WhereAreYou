package com.whereareyou.util

import android.util.Log
import java.util.Calendar

object CalendarUtil {

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

    private fun getDayOfWeek(year: Int, month: Int, date: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, date)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun getCalendarInfo(year: Int, month: Int): ArrayList<Int> {
        val calendar = Calendar.getInstance()
        // 첫 날 요일
        val firstDayOfWeek = getDayOfWeek(year, month, 1)

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, getDayOfMonth(year, month))
        val weekCount = calendar.get(Calendar.WEEK_OF_MONTH)

        calendar.set(Calendar.DATE, 2 - firstDayOfWeek)
        val arrList = ArrayList<Int>()
        for (i in 1..(7 * weekCount)) {
            arrList.add(calendar.time.toString().split(" ")[2].toInt())
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1)
        }
        return arrList
    }

    enum class Type {
        PREVIOUS, CURRENT, NEXT
    }

    fun getYearList(): ArrayList<Int> {
        val arrList = ArrayList<Int>()
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        for (i in currentYear - 100..currentYear + 100) arrList.add(i)

        return arrList
    }

    fun getTodayInfo(): List<Int> {
        val calendar = Calendar.getInstance()
        return listOf(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))
    }
}