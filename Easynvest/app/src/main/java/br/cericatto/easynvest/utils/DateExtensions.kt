package br.cericatto.easynvest.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

fun String.twoDecimals() : String {
    return when (this.length) {
        1 -> "0$this"
        2 -> this
        else -> this.substring(0, 2)
    }
}

fun String.formatMaturityDate() : String {
    var parts = this.split("/")
    return "${parts[2]}-${parts[1]}-${parts[0]}"
}

fun Long.dateIsInFuture() : Boolean {
    val current = Date(System.currentTimeMillis())
    val diff: Long = this - current.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    return days > 0
}

/**
 * Example Entry:
 * "2023-03-03T00:00:00"
  */
fun String.formatMaturityDateFromBackend() : String {
    val a = this.split("T")
    val b = a[0].split("-")
    return "${b[2]}/${b[1]}/${b[0]}"
}