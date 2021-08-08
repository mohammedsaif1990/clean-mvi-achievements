package com.kordia.achievements.presentation.activity.fragment.achievements

import com.kordia.achievements.presentation.appstructure.BaseIntent

/**
 * AchievementsIntent class.
 *
 * @author Mohammedsaif Kordia
 */
sealed class AchievementsIntent : BaseIntent() {

    /**
     * GetAchievementsEvent.
     */
    object GetAchievementsEvent : AchievementsIntent()
}