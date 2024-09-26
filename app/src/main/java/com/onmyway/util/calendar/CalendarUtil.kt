package com.onmyway.util.calendar

import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

// year년 month월에 몇일까지 있는지 return
fun getLastDayOfMonth(year: Int, month: Int, flag: Type = Type.Current): Int {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DATE, 1)
    calendar.set(Calendar.YEAR, year)
    when (flag) {
        Type.Previous -> calendar.set(Calendar.MONTH, month - 2)
        Type.Current -> calendar.set(Calendar.MONTH, month - 1)
        Type.Next -> calendar.set(Calendar.MONTH, month)
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
fun getCalendarInfo(year: Int, month: Int): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()

    // 입력받은 년도와 월로 LocalDate 객체 생성
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())

    // 첫 주를 일요일부터 시작하기 위해 첫 번째 날짜 앞의 일요일까지 채우기
    var startDay = firstDayOfMonth
    while (startDay.dayOfWeek != DayOfWeek.SUNDAY) {
        startDay = startDay.minusDays(1)
    }

    // 마지막 주를 토요일까지 채우기 위해 마지막 날짜 뒤의 토요일까지 채우기
    var endDay = lastDayOfMonth
    while (endDay.dayOfWeek != DayOfWeek.SATURDAY) {
        endDay = endDay.plusDays(1)
    }

    // 시작일부터 종료일까지 날짜를 리스트에 추가
    var currentDay = startDay
    while (currentDay <= endDay) {
        dates.add(currentDay)
        currentDay = currentDay.plusDays(1)
    }

    return dates
}

fun getYearCalendars(startYear: Int, endYear: Int): List<List<List<LocalDate>>> {
    val allCalendarsByYear = mutableListOf<List<List<LocalDate>>>()

    for (year in startYear..endYear) {
        val yearCalendars = mutableListOf<List<LocalDate>>()
        for (month in 1..12) {
            yearCalendars.add(getCalendarInfo(year, month))
        }
        allCalendarsByYear.add(yearCalendars)
    }

    return allCalendarsByYear
}

enum class Type {
    Previous, Current, Next
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
    return Calendar.getInstance().apply { time = date }
}

fun getMinuteDiffWithCurrentTime(time: String): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val t = dateFormat.parse(time)
    val currentDate = Date()
    val diffInMillis = t.time - currentDate.time
    val minutes = diffInMillis / (1000 * 60)
    return minutes.toInt().absoluteValue
}

fun compareDate(dateTime1: LocalDateTime, dateTime2: LocalDateTime): Boolean {
    return dateTime1.year == dateTime2.year &&
            dateTime1.month == dateTime2.month &&
            dateTime1.dayOfMonth == dateTime2.dayOfMonth
}

fun compareDate(dateTime1: LocalDate, dateTime2: LocalDate): Boolean {
    return dateTime1.year == dateTime2.year &&
            dateTime1.month == dateTime2.month &&
            dateTime1.dayOfMonth == dateTime2.dayOfMonth
}

fun parseLocalDateTime(str: String): LocalDateTime {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    return LocalDateTime.parse(str, dateFormat)
}

fun parseLocalDate(str: String): LocalDate {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    val ldt =  LocalDateTime.parse(str, dateFormat)
    return LocalDate.of(ldt.year, ldt.monthValue, ldt.dayOfMonth)
}