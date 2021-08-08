package com.kordia.achievements.presentation.activity.fragment.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kordia.achievements.domain.model.GoalDto
import com.kordia.achievements.domain.repository.GoalRepository
import com.kordia.achievements.domain.utils.DataState
import com.kordia.achievements.presentation.appstructure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * GoalsViewModel class.
 *
 * @author Mohammedsaif Kordia
 */
@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : BaseViewModel<GoalsIntent>() {

    private val _text = MutableLiveData("No goals to show")
    val text: LiveData<String> get() = _text

    private val _goalsLiveData = MutableLiveData<List<GoalDto>>()
    val goalsLiveData: LiveData<List<GoalDto>> get() = _goalsLiveData

    /**
     * Gets all goals.
     */
    private suspend fun getGoals() =

        goalRepository.getAll()
            .map { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        _goalsLiveData.postValue(listOf())
                        _loadingLiveData.postValue(false)
                        _text.postValue("No goals to show")
                    }
                    is DataState.Error -> {
                        _errorLiveData.postValue(dataState.exception)
                        _loadingLiveData.postValue(false)
                    }
                    is DataState.Loading -> {
                        _loadingLiveData.postValue(true)
                    }
                    is DataState.Success -> {
                        _goalsLiveData.postValue(dataState.data!!)
                        _loadingLiveData.postValue(false)
                        _text.postValue("")
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Triggers the intent events.
     *
     * @param eventType the event type that we want to trigger
     */
    override fun onTriggerEvent(eventType: GoalsIntent) {

        viewModelScope.launch {
            when (eventType) {
                GoalsIntent.GetGoalsEvent -> getGoals()
            }
        }
    }
}