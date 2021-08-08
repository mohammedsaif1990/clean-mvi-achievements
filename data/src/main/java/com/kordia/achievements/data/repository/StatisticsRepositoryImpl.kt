package com.kordia.achievements.data.repository

import com.kordia.achievements.data.db.achievement.AchievementDao
import com.kordia.achievements.data.db.goal.GoalDao
import com.kordia.achievements.data.db.setting.SettingDao
import com.kordia.achievements.domain.model.HomeStatisticsDto
import com.kordia.achievements.domain.repository.StatisticsRepository
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Statistics repository class.
 *
 * @author Mohammedsaif Kordia
 */
class StatisticsRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao,
    private val goalDao: GoalDao,
    private val settingDao: SettingDao

) : StatisticsRepository {

    /**
     * Get home statistics.
     *
     * @return details and statistics about the achievements and goals
     */
    override suspend fun getHomeStatistics(): Flow<DataState<HomeStatisticsDto>> = flow {
        try {
            emit(DataState.Loading)
            val currentUserId = settingDao.getCurrentUserId()
            val achievementsCount = achievementDao.getCountByUser(currentUserId)
            val goalsAchievedCount = goalDao.getAchievedCountByUser(currentUserId)
            val goalsCount = goalDao.getGoalsCountByUser(currentUserId)
            val goalsWaitingCount = goalsCount.minus(goalsAchievedCount)
            emit(
                DataState.Success(
                    HomeStatisticsDto(
                        achievements = achievementsCount,
                        achieved = goalsAchievedCount,
                        goalsTotal = goalsCount,
                        goalsWaiting = goalsWaitingCount
                    )
                )
            )
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}