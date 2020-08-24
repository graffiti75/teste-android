package br.cericatto.easynvest.utils

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