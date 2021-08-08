package com.kordia.achievements.presentation.activity.fragment.home

import com.kordia.achievements.presentation.appstructure.BaseIntent

/**
 * HomeIntent class.
 *
 * @author Mohammedsaif Kordia
 */
sealed class HomeIntent: BaseIntent() {

    /**
     * GetHomStatisticsEvent.
     */
    object GetHomStatisticsEvent: HomeIntent()
}
