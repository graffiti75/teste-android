package br.cericatto.easynvest.utils

import android.content.Context
import android.widget.Toast
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(message: Int) {
    Toast.makeText(this, this.getString(message), Toast.LENGTH_LONG).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun String.twoDecimals() : String {
    return when (this.length) {
        1 -> this + "0"
        2 -> this
        else -> this.substring(0, 2)
    }
}

fun String.formatTicketValue() : String {
    var text = this
    if (text.contains(",")) text = text.replace(",", ".")
    val split = text.split(".")
    val left = split[0].removeCurrencyCharacters()
    val right = split[1].removeCurrencyCharacters()
    val value = "$left.${right.twoDecimals()}"
    return value.replace(".", ",")
}

private fun String.removeCurrencyCharacters() : String {
    return this.replace("R", "")
        .replace("$", "")
        .replace(",", "")
}

fun Double.doubleToCurrency() : String {
    val decimal = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    var output = numberFormat.format((decimal.toDouble()))
    output = output.replace(".", ";")
        .replace(",", ".")
        .replace(";", ",")
        .replace("$", "")
    return "R$ $output"
}

fun String.formatDate() : String {
    val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this.toLong()
    return formatter.format(calendar.time)
}

fun Long.formatDateHour() : String {
    val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm")
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun String.formatDateHour() : String {
    val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm")
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this.toLong()
    return formatter.format(calendar.time)
}

fun String.trimAll() : String {
    return this.trimStart().trimEnd()
}