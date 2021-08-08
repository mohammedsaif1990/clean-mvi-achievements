package com.kordia.achievements.domain.utils

/**
 * EventState class is a wrapper class to emit UI state.
 *
 * @author Mohammedsaif Kordia
 */
sealed class EventState {

    /**
     * Back is to emit the back state, we use this after finishing an event
     * and we don't need the current fragment anymore.
     */
    object Back : EventState()
}
