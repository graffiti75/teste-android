package br.cericatto.easynvest.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EasyProperty(
    val investmentParameter: InvestmentParameter = InvestmentParameter(),
    val grossAmount: Double = 60528.20,
    val taxesAmount: Double = 4230.78,
    val netAmount: Double = 56297.42,
    val grossAmountProfit: Double = 28205.20,
    val netAmountProfit: Double = 23974.42,
    val annualGrossRateProfit: Double = 87.26,
    val monthlyGrossRateProfit: Double = 0.76,
    val dailyGrossRateProfit: Double = 0.000445330025305748,
    val taxesRate: Double = 15.0,
    val rateProfit: Double = 9.5512,
    val annualNetRateProfit: Double = 74.17
): Parcelable

@Parcelize
data class InvestmentParameter(
    val investedAmount: Double = 32323.0,
    val yearlyInterestRate: Double= 9.5512,
    val maturityTotalDays: Int = 1981,
    val maturityBusinessDays: Int = 1409,
    val maturityDate: String = "2023-03-03T00:00:00",
    val rate: Double = 123.0,
    val isTaxFree: Boolean = false
): Parcelable