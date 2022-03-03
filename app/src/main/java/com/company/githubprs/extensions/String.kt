package com.company.githubprs.extensions

import java.time.DateTimeException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toHumanReadableDate(): String {
    return try {
        val localDate = ZonedDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        localDate.format(DateTimeFormatter.RFC_1123_DATE_TIME)
    } catch (e: DateTimeException) {
        this
    }
}