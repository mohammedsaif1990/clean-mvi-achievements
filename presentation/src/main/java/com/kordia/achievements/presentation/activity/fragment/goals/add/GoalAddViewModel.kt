package com.kordia.achievements.presentation.activity.fragment.goals.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kordia.achievements.domain.model.GoalDto
import com.kordia.achievements.domain.repository.GoalRepository
import com.kordia.achievements.domain.utils.DataState
import com.kordia.achievements.domain.utils.EventState
import com.kordia.achievements.presentation.appstructure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * GoalAddViewModel class.
 *
 * @author Mohammedsaif Kordia
 */
@HiltViewModel
class GoalAddViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : BaseViewModel<GoalAddIntent>() {

    private val _goalLiveData = MutableLiveData(GoalDto(dateExpected = Date().time))
    val goalLiveData: LiveData<GoalDto> get() = _goalLiveData

    var goalId = "-1"

    /**
     * Create a goal.
     *
     * @param goal the goal to create
     */
    private suspend fun create(goal: GoalDto) =

        goalRepository.create(goal)
            .map { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        _loadingLiveData.postValue(false)
                    }
                    is DataState.Error -> {
                        _loadingLiveData.postValue(false)
                        _errorLiveData.postValue(dataState.exception)
                    }
                    is DataState.Loading -> {
                        _loadingLiveData.postValue(true)
                    }
                    is DataState.Success -> {
                        _loadingLiveData.postValue(false)
                        _messageLiveData.postValue("Goal added!")
                        dataState.data.let { achievement ->
                            _goalLiveData.postValue(achievement)
                        }
                        _eventStateLiveData.postValue(EventState.Back)
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Get a goal.
     *
     * @param id the goal id to get
     */
    private suspend fun get(id: String) =

        goalRepository.get(id)
            .map { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        _loadingLiveData.postValue(false)
                    }
                    is DataState.Error -> {
                        _loadingLiveData.postValue(false)
                        _errorLiveData.postValue(dataState.exception)
                    }
                    is DataState.Loading -> {
                        _loadingLiveData.postValue(true)
                    }
                    is DataState.Success -> {
                        _loadingLiveData.postValue(false)
                        dataState.data.let { goal ->
                            _goalLiveData.postValue(goal)
                        }
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Update a goal.
     *
     * @param goal the goal to update
     */
    private suspend fun update(goal: GoalDto) =

        goalRepository.update(goal)
            .map { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        _loadingLiveData.postValue(false)
                    }
                    is DataState.Error -> {
                        _loadingLiveData.postValue(false)
                        _errorLiveData.postValue(dataState.exception)
                    }
                    is DataState.Loading -> {
                        _loadingLiveData.postValue(true)
                    }
                    is DataState.Success -> {
                        _loadingLiveData.postValue(false)
                        _messageLiveData.postValue("Goal updated!")
                        dataState.data.let { achievement ->
                            _goalLiveData.postValue(achievement)
                        }
                        _eventStateLiveData.postValue(EventState.Back)
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Delete the current presented goal.
     */
    private suspend fun delete() =

        goalRepository.delete(goalId)
            .map { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        _loadingLiveData.postValue(false)
                    }
                    is DataState.Error -> {
                        _loadingLiveData.postValue(false)
                        _errorLiveData.postValue(dataState.exception)
                    }
                    is DataState.Loading -> {
                        _loadingLiveData.postValue(true)
                    }
                    is DataState.Success -> {
                        _loadingLiveData.postValue(false)
                        _messageLiveData.postValue("Goal deleted!")
                        _eventStateLiveData.postValue(EventState.Back)
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Sets achieved for the current presented goal.
     */
    private fun setAchieved() {

        _goalLiveData.postValue(
            goalLiveData.value?.apply { achieved = !(goalLiveData.value?.achieved ?: false) }
        )
    }

    /**
     * Sets dateExpected for the current presented goal.
     *
     * @param date date to set
     */
    private fun setDate(date: Long) {

        _goalLiveData.postValue(
            goalLiveData.value?.apply { dateExpected = date }
        )
    }

    /**
     * Triggers the intent events.
     *
     * @param eventType the event type that we want to trigger
     */
    override fun onTriggerEvent(eventType: GoalAddIntent) {

        viewModelScope.launch {
            when (eventType) {
                is GoalAddIntent.CreateEvent -> goalLiveData.value?.let {
                    if (!it.title.isNullOrEmpty()) create(it)
                    else _messageLiveData.postValue("Title is required")
                }
                is GoalAddIntent.GetEvent -> get(goalId)
                is GoalAddIntent.UpdateEvent -> goalLiveData.value?.let {
                    if (!it.title.isNullOrEmpty()) update(it)
                    else _messageLiveData.postValue("Title is required")
                }
                is GoalAddIntent.DeleteEvent -> delete()
                is GoalAddIntent.SetAchievedEvent -> setAchieved()
                is GoalAddIntent.SetDateEvent -> setDate(eventType.date)
            }
        }
    }
}