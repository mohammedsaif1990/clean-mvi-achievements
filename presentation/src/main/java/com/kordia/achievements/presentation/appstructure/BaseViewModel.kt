package com.kordia.achievements.presentation.appstructure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kordia.achievements.domain.utils.EventState

/**
 * BaseViewModel class the parent of all view models.
 *
 * @author Mohammedsaif Kordia
 */
@Suppress("PropertyName", "UNCHECKED_CAST")
abstract class BaseViewModel<T> : ViewModel() {

    protected val _errorLiveData = MutableLiveData<Exception>()
    val errorLiveData: LiveData<Exception> get() = _errorLiveData

    protected val _eventStateLiveData = MutableLiveData<EventState>()
    val eventStateLiveData: LiveData<EventState> get() = _eventStateLiveData

    protected val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    protected val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = _messageLiveData

    private var _intent: BaseIntent? = null

    protected val event: T
        get() = _intent as T

    abstract fun onTriggerEvent(eventType: T)
}