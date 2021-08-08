package com.kordia.achievements.presentation.activity.fragment.goals

import com.kordia.achievements.presentation.appstructure.BaseIntent

/**
 * GoalsIntent class.
 *
 * @author Mohammedsaif Kordia
 */
sealed class GoalsIntent : BaseIntent() {

    /**
     * GetGoalsEvent.
     */
    object GetGoalsEvent : GoalsIntent()
}
