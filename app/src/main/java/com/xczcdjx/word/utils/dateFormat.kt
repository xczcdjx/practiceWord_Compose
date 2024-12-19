package com.xczcdjx.word.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

// UTC 转标准时间
fun formatDate(
    isoDateString: String,
    showTime: Boolean = false,
    zone: TimeZone = TimeZone.currentSystemDefault()
): String {
    val customFormat = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        if (showTime) {
            char(' ')
            hour(); char(':'); minute(); char(':'); second()
//        char(','); secondFraction(fixedLength = 3)
        }
    }
    val instant = Instant.parse(isoDateString)
    val localDateTime = instant.toLocalDateTime(zone)
    return localDateTime.format(customFormat)
}

// Instant 转 标准时间
fun formatNow(
    now: Instant = Clock.System.now(),
    showTime: Boolean = false,
    zone: TimeZone = TimeZone.currentSystemDefault()
): String {
    val cF = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        if (showTime) {
            char(' ')
            hour(); char(':'); minute(); char(':'); second()
//        char(','); secondFraction(fixedLength = 3)
        }
    }
    val localDT = now.toLocalDateTime(zone)
    return localDT.format(cF)
}

// 字符串转时间戳
fun convertStrToMill(str: String, zone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val format = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char(' ')
        hour(); char(':'); minute(); char(':'); second()
    }
    return LocalDateTime.parse(str, format).toInstant(zone)
        .toEpochMilliseconds()
}

// 时间戳转回标准时间
fun convertMillToDate(
    m: Long,
    showTime: Boolean = false,
    zone: TimeZone = TimeZone.currentSystemDefault()
): String {
    return formatNow(Instant.fromEpochMilliseconds(m), showTime, zone)
}