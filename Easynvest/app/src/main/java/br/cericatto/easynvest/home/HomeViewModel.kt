package br.cericatto.easynvest.home

import androidx.lifecycle.*
import br.cericatto.easynvest.EasyApiStatus
import br.cericatto.easynvest.network.EasyApi
import br.cericatto.easynvest.network.EasyProperty
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    /**
     * Api.
     */

    private val _status = MutableLiveData<EasyApiStatus>()
    val status: LiveData<EasyApiStatus>
        get() = _status

    private val _properties = MutableLiveData<EasyProperty>()
    val properties: LiveData<EasyProperty>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<EasyProperty>()
    val navigateToSelectedProperty: LiveData<EasyProperty>
        get() = _navigateToSelectedProperty

    val _simulateButtonVisible = MutableLiveData<Boolean>()
    val simulateButtonVisible: LiveData<Boolean>
        get() = _simulateButtonVisible

    /**
     * Init.
     */

    init {
        _simulateButtonVisible.value = false
    }

    /**
     * Sets the value of the status LiveData to the Easy API status.
     */

    fun getEasynvestData(investedAmount: Double, rate: Double, maturityDate: String) {
        viewModelScope.launch {
            _status.value = EasyApiStatus.LOADING
            try {
                _properties.value = EasyApi.retrofitService.getProperties(
                    investedAmount = investedAmount,
                    rate = rate,
                    maturityDate = maturityDate
                )
                _status.value = EasyApiStatus.DONE
                displayPropertyDetails(properties.value!!)
            } catch (e: Exception) {
                _status.value = EasyApiStatus.ERROR
                _properties.value = EasyProperty()
            }
        }
    }

    fun checkFields(howMuchToApply: Double, cdi: Double, date: String) {
        val validInvestment = howMuchToApply > 0
        val validCdi = cdi > 0
        val validDate = date.isNotEmpty()
        if (validInvestment && validCdi && validDate) {
            _simulateButtonVisible.value = true
            getEasynvestData(investedAmount = howMuchToApply, rate = cdi, maturityDate = date)
        } else {
            _simulateButtonVisible.value = false
        }
    }

    private fun displayPropertyDetails(easyProperty: EasyProperty) {
        _navigateToSelectedProperty.value = easyProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}