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

fun String.percentageToDouble() : Double {
    return this.replace(",", ";")
        .replace(".", "")
        .replace(";", ".")
        .replace("%", "")
        .toDouble()
}

fun String.formatPercentage() : String {
    val number = this.replace("%", "")
        .replace(",", ";")
        .replace(".", "")
        .replace(";", ".")
        .toDouble()
    val decimal = BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN)
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val currency = numberFormat.format((decimal.toDouble()))
    return currency.replace(",", "")
        .replace("$", "")
}

fun Double.formatPercentage() : String {
    val decimal = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val currency = numberFormat.format((decimal.toDouble()))
    return currency.replace(".", ";")
        .replace(",", ".")
        .replace(";", ",")
        .replace("$", "")
}

/**
 * Example Entry:
 * "0.36"
 */
fun Double.formatPercentageFromBackend() : String {
    val a = this.toString().split(".")
    return "${a[0]},${a[1].twoDecimals()}%"
}

fun Double.doubleToCurrency(includeCurrencySign: Boolean = true) : String {
    val decimal = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    var output = numberFormat.format((decimal.toDouble()))
    output = output.replace(".", ";")
        .replace(",", ".")
        .replace(";", ",")
        .replace("$", "")
    return if (includeCurrencySign) "R$ $output" else "${output.replace(" ", "")}"
}

fun String.cleanCurrencyCharacters() : String {
    return this.replace("R", "")
        .replace("$", "")
        .replace(".", "")
        .replace(",", ".")
        .replace(" ", "")
}