package com.kordia.achievements.domain.repository

import com.kordia.achievements.domain.model.GoalDto
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Goal repository interface.
 *
 * @author Mohammedsaif Kordia
 */
interface GoalRepository {

    /**
     * Create a goal.
     *
     * @param goal the goal to create
     * @return      a flow of created goal wrapped with DataState
     */
    suspend fun create(goal: GoalDto): Flow<DataState<GoalDto>>

    /**
     * Get a goal by id.
     *
     * @param id the id for the goal to get
     * @return   a flow of goal with the specified id wrapped with DataState
     */
    suspend fun get(id: String): Flow<DataState<GoalDto>>

    /**
     * Get all goals.
     *
     * @return a flow of all goals wrapped with DataState
     */
    suspend fun getAll(): Flow<DataState<List<GoalDto>>>

    /**
     * Update a goal.
     *
     * @param goal the goal to update
     * @return     a flow of updated goal wrapped with DataState
     */
    suspend fun update(goal: GoalDto): Flow<DataState<GoalDto>>

    /**
     * Delete a goal.
     *
     * @param id the id for the goal to delete
     * @return   a flow of deleted goal id wrapped with DataState
     */
    suspend fun delete(id: String): Flow<DataState<String>>
}