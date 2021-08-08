package com.kordia.achievements.data.repository

import com.kordia.achievements.data.api.achievement.AchievementApi
import com.kordia.achievements.data.db.achievement.AchievementDao
import com.kordia.achievements.data.db.user.UserDao
import com.kordia.achievements.data.mapper.toAchievementData
import com.kordia.achievements.data.mapper.toAchievementDataList
import com.kordia.achievements.data.mapper.toAchievementDto
import com.kordia.achievements.data.mapper.toAchievementDtoList
import com.kordia.achievements.domain.Constants
import com.kordia.achievements.domain.model.AchievementDto
import com.kordia.achievements.domain.repository.AchievementRepository
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

/**
 * Achievement repository class.
 *
 * @author Mohammedsaif Kordia
 */
class AchievementRepositoryImpl @Inject constructor(
    private val achievementApi: AchievementApi,
    private val achievementDao: AchievementDao,
    private val userDao: UserDao
) : AchievementRepository {

    /**
     * Create an achievement.
     *
     * @param achievement the achievement to create
     * @return            a flow of created achievement wrapped with DataState
     */
    override suspend fun create(achievement: AchievementDto) = flow {
        try {
            emit(DataState.Loading)
            val mId = UUID.randomUUID().toString()
            val user = userDao.getCurrentUser()
            if (user == null || user.id == Constants.GUEST_USER_ID) {
                val achievementData =
                    achievement.apply {
                        id = Constants.GUEST_USER_ID.plus("::").plus(mId)
                        userId = Constants.GUEST_USER_ID
                    }.toAchievementData()
                val insert = achievementDao.insert(achievementData)
                if (insert == -1L)
                    emit(DataState.Error(Exception("Insert error")))
                else
                    emit(DataState.Success(achievementData.toAchievementDto()))
            } else {
                val achievementDto =
                    achievement.apply {
                        id = user.id.plus("::").plus(mId)
                        userId = user.id
                    }
                val onlineAchievement =
                    achievementApi.create(achievementDto, "Bearer ".plus(user.token))
                val insert = achievementDao.insert(onlineAchievement.toAchievementData())
                if (insert == -1L)
                    emit(DataState.Error(Exception("Insert error")))
                else
                    emit(DataState.Success(onlineAchievement))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    /**
     * Get an achievement by id.
     *
     * @param id the id for the achievement to get
     * @return   a flow of achievement with the specified id wrapped with DataState
     */
    override suspend fun get(id: String): Flow<DataState<AchievementDto>> = flow {
        emit(DataState.Loading)
        try {
            val achievement = achievementDao.get(id)
            if (achievement != null) {
                emit(DataState.Success(achievement.toAchievementDto()))
            } else {
                emit(DataState.Empty)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    /**
     * Get all achievements.
     *
     * @return a flow of all achievements wrapped with DataState
     */
    override suspend fun getAll() = flow {
        emit(DataState.Loading)
        try {
            val user = userDao.getCurrentUser()
            if (user != null) {
                val token = userDao.getCurrentUserToken()
                val onlineAchievements = achievementApi.getAll("Bearer $token")
                onlineAchievements.toAchievementDataList().forEach { achievementData ->
                    achievementDao.insert(achievementData)
                }
                val localAchievements = achievementDao.getAll(user.id)
                if (localAchievements.isNotEmpty()) {
                    emit(DataState.Success(localAchievements.toAchievementDtoList()))
                } else {
                    emit(DataState.Empty)
                }
            } else {
                emit(DataState.Empty)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
            try {
                val user = userDao.getCurrentUser()
                if (user != null) {
                    val localAchievements = achievementDao.getAll(user.id)
                    if (localAchievements.isNotEmpty()) {
                        emit(DataState.Success(localAchievements.toAchievementDtoList()))
                    } else {
                        emit(DataState.Empty)
                    }
                } else {
                    emit(DataState.Empty)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(e))
            }
        }
    }

    /**
     * Update an achievement.
     *
     * @param achievement the achievement to update
     * @return            a flow of updated achievement wrapped with DataState
     */
    override suspend fun update(achievement: AchievementDto): Flow<DataState<AchievementDto>> =
        flow {
            try {
                emit(DataState.Loading)
                val user = userDao.getCurrentUser()
                if (user == null || user.id == Constants.GUEST_USER_ID) {
                    val achievementData = achievement.toAchievementData()
                    achievementDao.update(achievementData)
                    emit(DataState.Success(achievementData.toAchievementDto()))
                } else {
                    val onlineAchievement =
                        achievementApi.update(achievement, "Bearer ".plus(user.token))
                    achievementDao.update(onlineAchievement.toAchievementData())
                    emit(DataState.Success(onlineAchievement))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(e))
            }
        }

    /**
     * Delete an achievement.
     *
     * @param id the id for the achievement to delete
     * @return   a flow of deleted achievement id wrapped with DataState
     */
    override suspend fun delete(id: String): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        try {
            val user = userDao.getCurrentUser()
            if (user == null || user.id == Constants.GUEST_USER_ID) {
                achievementDao.delete(id)
                emit(DataState.Success(id))
            } else {
                achievementApi.delete(id, "Bearer ".plus(user.token))
                achievementDao.delete(id)
                emit(DataState.Success(id))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}