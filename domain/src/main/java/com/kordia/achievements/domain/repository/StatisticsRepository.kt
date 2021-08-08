package com.kordia.achievements.domain.repository

import com.kordia.achievements.domain.model.HomeStatisticsDto
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Statistics repository interface.
 *
 * @author Mohammedsaif Kordia
 */
interface StatisticsRepository {

    /**
     * Get home statistics.
     *
     * @return details and statistics about the achievements and goals
     */
    suspend fun getHomeStatistics(): Flow<DataState<HomeStatisticsDto>>
}