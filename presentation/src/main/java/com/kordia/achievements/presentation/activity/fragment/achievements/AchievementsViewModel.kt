package com.kordia.achievements.presentation.activity.fragment.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kordia.achievements.domain.model.AchievementDto
import com.kordia.achievements.domain.repository.AchievementRepository
import com.kordia.achievements.domain.utils.DataState
import com.kordia.achievements.presentation.appstructure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AchievementsViewModel class.
 *
 * @author Mohammedsaif Kordia
 */
@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val achievementRepository: AchievementRepository
) : BaseViewModel<AchievementsIntent>() {

    private val _text = MutableLiveData("No achievements to show")
    val text: LiveData<String> get() = _text

    private val _achievementsLiveData = MutableLiveData<List<AchievementDto>>()
    val achievementsLiveData: LiveData<List<AchievementDto>> get() = _achievementsLiveData

    /**
     * Gets all achievements.
     */
    private suspend fun getAchievements() =

        achievementRepository.getAll()
            .map { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        _achievementsLiveData.postValue(listOf())
                        _loadingLiveData.postValue(false)
                        _text.postValue("No achievements to show")
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
                        _achievementsLiveData.postValue(dataState.data!!)
                        _text.postValue("")
                    }
                }
            }.launchIn(viewModelScope)

    /**
     * Triggers the intent events.
     *
     * @param eventType the event type that we want to trigger
     */
    override fun onTriggerEvent(eventType: AchievementsIntent) {

        viewModelScope.launch {
            when (eventType) {
                AchievementsIntent.GetAchievementsEvent -> getAchievements()
            }
        }
    }
}