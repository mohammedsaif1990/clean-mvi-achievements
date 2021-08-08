package com.kordia.achievements.domain.repository

import com.kordia.achievements.domain.model.AchievementDto
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Achievement repository interface.
 *
 * @author Mohammedsaif Kordia
 */
interface AchievementRepository {

    /**
     * Create an achievement.
     *
     * @param achievement the achievement to create
     * @return            a flow of created achievement wrapped with DataState
     */
    suspend fun create(achievement: AchievementDto): Flow<DataState<AchievementDto>>

    /**
     * Get an achievement by id.
     *
     * @param id the id for the achievement to get
     * @return   a flow of achievement with the specified id wrapped with DataState
     */
    suspend fun get(id: String): Flow<DataState<AchievementDto>>

    /**
     * Get all achievements.
     *
     * @return a flow of all achievements wrapped with DataState
     */
    suspend fun getAll(): Flow<DataState<List<AchievementDto>>>

    /**
     * Update an achievement.
     *
     * @param achievement the achievement to update
     * @return            a flow of updated achievement wrapped with DataState
     */
    suspend fun update(achievement: AchievementDto): Flow<DataState<AchievementDto>>

    /**
     * Delete an achievement.
     *
     * @param id the id for the achievement to delete
     * @return   a flow of deleted achievement id wrapped with DataState
     */
    suspend fun delete(id: String): Flow<DataState<String>>
}