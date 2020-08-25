package br.cericatto.easynvest.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.cericatto.easynvest.network.EasyProperty
import br.cericatto.easynvest.utils.doubleToCurrency
import br.cericatto.easynvest.utils.formatMaturityDateFromBackend
import br.cericatto.easynvest.utils.formatPercentageFromBackend

class ResultViewModel : ViewModel() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    // The current EasyProperty.
    private val _easyProperty = MutableLiveData<EasyProperty>()
    val easyProperty: LiveData<EasyProperty>
        get() = _easyProperty

    val simulationResultValue = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.grossAmount.doubleToCurrency()
    }

    val initialValue = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.investmentParameter.investedAmount.doubleToCurrency()
    }

    val rawValue = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.grossAmount.doubleToCurrency()
    }

    val earnedValue = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.grossAmountProfit.doubleToCurrency()
    }
    val earnedValueForColored = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.grossAmountProfit.doubleToCurrency(false)
    }

    val formattedTaxValue = Transformations.map(easyProperty) { easyProperty ->
        val taxesAmount = easyProperty.taxesAmount
        val taxesRate = easyProperty.taxesRate
        "${taxesAmount.doubleToCurrency()} (${taxesRate.formatPercentageFromBackend()})"
        "${taxesAmount.doubleToCurrency()} (${taxesRate.formatPercentageFromBackend()})"
    }

    val netValue = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.netAmount.doubleToCurrency()
    }

    val dateValue = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.investmentParameter.maturityDate.formatMaturityDateFromBackend()
    }

    val daysValue = Transformations.map(easyProperty) { easyProperty ->
        easyProperty.investmentParameter.maturityTotalDays.toString()
    }

    val monthlyIncomeValue = Transformations.map(easyProperty) { easyProperty ->
        "${easyProperty.monthlyGrossRateProfit.formatPercentageFromBackend()}"
    }

    val cdiValue = Transformations.map(easyProperty) { easyProperty ->
        "${easyProperty.investmentParameter.rate.formatPercentageFromBackend()}"
    }

    val annualProfitabilityValue = Transformations.map(easyProperty) { easyProperty ->
        "${easyProperty.annualNetRateProfit.formatPercentageFromBackend()}"
    }

    val periodProfitabilityValue = Transformations.map(easyProperty) { easyProperty ->
        "${easyProperty.annualGrossRateProfit.formatPercentageFromBackend()}"
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    fun updateEasyProperty(value: EasyProperty) {
        _easyProperty.value = value
    }
}