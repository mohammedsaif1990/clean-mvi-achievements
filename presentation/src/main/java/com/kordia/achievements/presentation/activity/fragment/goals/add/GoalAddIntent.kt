package com.kordia.achievements.presentation.activity.fragment.goals.add

import com.kordia.achievements.presentation.appstructure.BaseIntent

/**
 * GoalAddIntent class.
 *
 * @author Mohammedsaif Kordia
 */
sealed class GoalAddIntent : BaseIntent() {

    /**
     * CreateEvent.
     */
    object CreateEvent : GoalAddIntent()

    /**
     * GetEvent.
     */
    object GetEvent : GoalAddIntent()

    /**
     * UpdateEvent.
     */
    object UpdateEvent : GoalAddIntent()

    /**
     * DeleteEvent.
     */
    object DeleteEvent : GoalAddIntent()

    /**
     * SetAchievedEvent.
     */
    object SetAchievedEvent : GoalAddIntent()

    /**
     * SetDateEvent.
     *
     * @param date date to set
     */
    data class SetDateEvent(val date: Long) : GoalAddIntent()
}
