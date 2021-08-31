package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    protected var loading: Boolean = false
        set(value) = _isLoading.postValue(value)

    private val _currentException = MutableLiveData<Exception>(null)
    protected var currentException: Exception = Exception()
        set(value) = _currentException.postValue(value)

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val exception: LiveData<Exception>
        get() = _currentException
}
