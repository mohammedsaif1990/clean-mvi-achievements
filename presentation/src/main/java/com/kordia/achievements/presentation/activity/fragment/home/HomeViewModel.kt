package com.kordia.achievements.presentation.activity.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kordia.achievements.domain.model.HomeStatisticsDto
import com.kordia.achievements.domain.repository.StatisticsRepository
import com.kordia.achievements.domain.utils.DataState
import com.kordia.achievements.presentation.appstructure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HomeViewModel class.
 *
 * @author Mohammedsaif Kordia
 */
@HiltViewModel
@Suppress("PropertyName")
class HomeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel<HomeIntent>() {

    private val _homeStatisticsLiveData = MutableLiveData<HomeStatisticsDto>()
    val homeStatisticsLiveData: LiveData<HomeStatisticsDto> get() = _homeStatisticsLiveData

    private val _text = MutableLiveData("This is home Fragment")
    val text: LiveData<String> get() = _text

    /**
     * Gets home statistics.
     */
    private suspend fun getHomeStatistics() =
        statisticsRepository.getHomeStatistics()
            .map { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        _loadingLiveData.postValue(false)
                    }
                    is DataState.Error -> {
                        _errorLiveData.postValue(dataState.exception)
                        _loadingLiveData.postValue(false)
                    }
                    is DataState.Loading -> {
                        _loadingLiveData.postValue(true)
                    }
                    is DataState.Success -> {
                        _loadingLiveData.postValue(false)
                        dataState.data.let { _homeStatisticsLiveData.postValue(it) }
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Triggers the intent events.
     *
     * @param eventType the event type that we want to trigger
     */
    override fun onTriggerEvent(eventType: HomeIntent) {
        viewModelScope.launch {
            when (eventType) {
                HomeIntent.GetHomStatisticsEvent -> getHomeStatistics()
            }
        }
    }
}