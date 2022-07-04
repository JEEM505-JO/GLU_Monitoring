package com.devnic.gmonitoring.util

import java.util.*

class CalendarClass {
    fun getdaystart(month: Int, monthday: Int): Calendar {
        val calendar: Calendar = Calendar.getInstance()
        when (month) {
            1 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.JANUARY)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            2 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.FEBRUARY)
                calendar.get(Calendar.DAY_OF_MONTH + monthday)
            }
            3 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.MARCH)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            4 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.APRIL)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            5 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.MAY)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)

            }
            6 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.JUNE)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            7 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.JULY)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            8 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.AUGUST)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            9 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.SEPTEMBER)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            10 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.OCTOBER)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            11 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.NOVEMBER)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            12 -> {
                calendar.set(Calendar.MONTH + 1, Calendar.DECEMBER)
                calendar.set(Calendar.DAY_OF_MONTH, monthday)
            }
            else -> {
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE))
            }
        }
        return calendar
    }

    fun getdayend(month: Int): Calendar {
        val calendar: Calendar = Calendar.getInstance()
        var daymax : Int = 0
        when (month + 1) {
            1 -> {
                calendar.set(Calendar.MONTH, Calendar.JANUARY)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            2 -> {
                calendar.set(Calendar.MONTH, Calendar.FEBRUARY)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            }
            3 -> {
                calendar.set(Calendar.MONTH, Calendar.MARCH)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            4 -> {
                calendar.set(Calendar.MONTH, Calendar.APRIL)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            }
            5 -> {
                calendar.set(Calendar.MONTH, Calendar.MAY)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            6 -> {
                calendar.set(Calendar.MONTH, Calendar.JUNE)
                daymax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                calendar.set(Calendar.DAY_OF_MONTH,daymax)
            }
            7 -> {
                calendar.set(Calendar.MONTH, Calendar.JULY)
                daymax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                calendar.set(Calendar.DAY_OF_MONTH,daymax)
            }
            8 -> {
                calendar.set(Calendar.MONTH, Calendar.AUGUST)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            9 -> {
                calendar.set(Calendar.MONTH, Calendar.SEPTEMBER)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            }
            10 -> {
                calendar.set(Calendar.MONTH, Calendar.OCTOBER)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            }
            11 -> {
                calendar.set(Calendar.MONTH, Calendar.NOVEMBER)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            12 -> {
                calendar.set(Calendar.MONTH, Calendar.DECEMBER)
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            else -> {
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE))
            }
        }
        return calendar
    }
}