package com.kordia.achievements.presentation.activity.fragment.achievements.add

import com.kordia.achievements.presentation.appstructure.BaseIntent

/**
 * AchievementAddIntent class.
 *
 * @author Mohammedsaif Kordia
 */
sealed class AchievementAddIntent : BaseIntent() {

    /**
     * CreateEvent.
     */
    object CreateEvent : AchievementAddIntent()

    /**
     * GetEvent.
     */
    object GetEvent : AchievementAddIntent()

    /**
     * UpdateEvent.
     */
    object UpdateEvent : AchievementAddIntent()

    /**
     * DeleteEvent.
     */
    object DeleteEvent : AchievementAddIntent()

    /**
     * SetDateEvent.
     */
    data class SetDateEvent(val date: Long) : AchievementAddIntent()
}