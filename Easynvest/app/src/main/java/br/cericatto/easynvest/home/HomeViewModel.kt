package br.cericatto.easynvest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.cericatto.easynvest.network.EasyApi
import br.cericatto.easynvest.network.EasyProperty
import br.cericatto.easynvest.utils.EasyApiStatus
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * API.
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

    /**
     * UI Fields.
     */

    val _startup = MutableLiveData<Boolean>()
    val startup: LiveData<Boolean>
        get() = _startup

    val _investmentEditTextIsValid = MutableLiveData<Boolean>()
    val investmentEditTextIsValid: LiveData<Boolean>
        get() = _investmentEditTextIsValid

    val _dateEditTextIsValid = MutableLiveData<Boolean>()
    val dateEditTextIsValid: LiveData<Boolean>
        get() = _dateEditTextIsValid

    val _timeMillis = MutableLiveData<Long>()
    val timeMillis: LiveData<Long>
        get() = _timeMillis

    val _cdiEditTextIsValid = MutableLiveData<Boolean>()
    val cdiEditTextIsValid: LiveData<Boolean>
        get() = _cdiEditTextIsValid

    val _simulateButtonVisible = MutableLiveData<Boolean>()
    val simulateButtonVisible: LiveData<Boolean>
        get() = _simulateButtonVisible

    /**
     * Entry Data.
     */

    val _investment = MutableLiveData<Double>()
    val investment: LiveData<Double>
        get() = _investment

    val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    val _cdi = MutableLiveData<Double>()
    val cdi: LiveData<Double>
        get() = _cdi

    //--------------------------------------------------
    // Init
    //--------------------------------------------------

    init {
        _startup.value = true
        _timeMillis.value = 0

        _investmentEditTextIsValid.value = false
        _cdiEditTextIsValid.value = false
        _dateEditTextIsValid.value = false
        _simulateButtonVisible.value = false
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    /**
     * Retrofit.
     */

    fun getEasynvestData() {
        getEasynvestData(_investment.value!!, _cdi.value!!, _date.value!!)
    }

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

    /**
     * UI Fields.
     */

    fun updateStartup() {
        _startup.value = false
    }

    fun updateInvestmentEditText(valid: Boolean, value: Double) {
        _investmentEditTextIsValid.value = valid
        _investment.value = value
        updateButtonVisibility()
    }

    fun updateCdiEditText(valid: Boolean, value: Double) {
        _cdiEditTextIsValid.value = valid
        _cdi.value = value
        updateButtonVisibility()
    }

    fun updateDateTextView(valid: Boolean, value: String) {
        _dateEditTextIsValid.value = valid
        _date.value = value
        updateButtonVisibility()
    }

    fun setTimeMillis(millis: Long) {
        _timeMillis.value = millis
    }

    fun getTimeMillis() : Long {
        return _timeMillis.value!!
    }

    fun updateButtonVisibility() {
        val investment = _investmentEditTextIsValid.value
        val date = _dateEditTextIsValid.value
        val cdi = _cdiEditTextIsValid.value
        _simulateButtonVisible.value = investment!! && date!! && cdi!!
    }

    /**
     * Screen Transition.
     */

    private fun displayPropertyDetails(easyProperty: EasyProperty) {
        _navigateToSelectedProperty.value = easyProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}