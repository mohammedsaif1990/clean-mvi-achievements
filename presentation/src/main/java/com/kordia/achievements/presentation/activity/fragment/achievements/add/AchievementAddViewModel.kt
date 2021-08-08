package com.kordia.achievements.presentation.activity.fragment.achievements.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kordia.achievements.domain.model.AchievementDto
import com.kordia.achievements.domain.repository.AchievementRepository
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
 * AchievementAddViewModel class.
 *
 * @author Mohammedsaif Kordia
 */
@HiltViewModel
class AchievementAddViewModel @Inject constructor(
    private val achievementRepository: AchievementRepository
) : BaseViewModel<AchievementAddIntent>() {

    private val _achievementLiveData = MutableLiveData(AchievementDto(dateAchieved = Date().time))
    val achievementLiveData: LiveData<AchievementDto> get() = _achievementLiveData

    var achievementId: String = "-1"

    /**
     * Create an achievement.
     *
     * @param achievement the achievement to create
     */
    private suspend fun create(achievement: AchievementDto) =

        achievementRepository.create(achievement)
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
                        _messageLiveData.postValue("Achievement added!")
                        dataState.data.let { achievement ->
                            _achievementLiveData.postValue(achievement)
                        }
                        _eventStateLiveData.postValue(EventState.Back)
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Get an achievement.
     *
     * @param id the achievement id to get
     */
    private suspend fun get(id: String) =

        achievementRepository.get(id)
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
                        dataState.data.let { achievement ->
                            _achievementLiveData.postValue(achievement)
                        }
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Update an achievement.
     *
     * @param achievement the achievement to update
     */
    private suspend fun update(achievement: AchievementDto) =

        achievementRepository.update(achievement)
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
                        _messageLiveData.postValue("Achievement updated!")
                        dataState.data.let { achievement ->
                            _achievementLiveData.postValue(achievement)
                        }
                        _eventStateLiveData.postValue(EventState.Back)
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Delete the current presented achievement.
     */
    private suspend fun delete() =

        achievementRepository.delete(achievementId)
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
                        _messageLiveData.postValue("Achievement deleted!")
                        _eventStateLiveData.postValue(EventState.Back)
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Sets dateAchieved for the current presented achievement.
     *
     * @param date date to set
     */
    private fun setDate(date: Long) {

        _achievementLiveData.postValue(
            achievementLiveData.value?.apply {
                dateAchieved = date
            }
        )
    }

    /**
     * Triggers the intent events.
     *
     * @param eventType the event type that we want to trigger
     */
    override fun onTriggerEvent(eventType: AchievementAddIntent) {

        viewModelScope.launch {
            when (eventType) {
                is AchievementAddIntent.CreateEvent -> achievementLiveData.value?.let {
                    if (!it.title.isNullOrEmpty()) create(it)
                    else _messageLiveData.postValue("Title is required!")
                }
                is AchievementAddIntent.GetEvent -> get(achievementId)
                is AchievementAddIntent.UpdateEvent -> achievementLiveData.value?.let {
                    if (!it.title.isNullOrEmpty()) update(it)
                    else _messageLiveData.postValue("Title is required!")
                }
                is AchievementAddIntent.DeleteEvent -> delete()
                is AchievementAddIntent.SetDateEvent -> setDate(eventType.date)
            }
        }
    }
}