package br.cericatto.easynvest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    /**
     * Call getEasynvestData()() on init so we can display status immediately.
     */
    init {
        getEasynvestData()
    }

    /**
     * Sets the value of the status LiveData to the Easynvest API status.
     */
    private fun getEasynvestData() {
        _response.value = "Set the Easynvest API Response here!"
    }
}