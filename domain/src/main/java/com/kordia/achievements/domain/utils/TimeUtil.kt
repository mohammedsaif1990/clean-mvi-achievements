package com.kordia.achievements.domain.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Time helper functions.
 *
 * @author Mohammedsaif Kordia
 */
enum class TimePatters(val pattern: String) {
    DATE_FORMAT("dd/MM/yyyy"),
}

fun String.getDate(pattern: TimePatters): Date {
    val simDateFormat = SimpleDateFormat(pattern.pattern, Locale.getDefault())
    return simDateFormat.parse(this) ?: Date()
}

fun Long.getDate(): String {
    val simDateFormat =
        SimpleDateFormat(TimePatters.DATE_FORMAT.pattern, Locale.getDefault())
    return simDateFormat.format(Date(this))
}