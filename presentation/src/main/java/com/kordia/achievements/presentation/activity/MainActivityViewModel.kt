package com.kordia.achievements.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * MainActivityViewModel class.
 *
 * @author Mohammedsaif Kordia
 */
@HiltViewModel
@Suppress("PropertyName")
class MainActivityViewModel @Inject constructor(
) : ViewModel() {

    private val _loadingLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    /**
     * Show loading.
     */
    fun showLoading(isLoading: Boolean) = _loadingLiveData.postValue(isLoading)
}