package br.cericatto.easynvest.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

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