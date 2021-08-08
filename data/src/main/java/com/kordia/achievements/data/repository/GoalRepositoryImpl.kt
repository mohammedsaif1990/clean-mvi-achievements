package com.kordia.achievements.data.repository

import com.kordia.achievements.data.api.goal.GoalApi
import com.kordia.achievements.data.db.goal.GoalDao
import com.kordia.achievements.data.db.user.UserDao
import com.kordia.achievements.data.mapper.toGoalData
import com.kordia.achievements.data.mapper.toGoalDataList
import com.kordia.achievements.data.mapper.toGoalDto
import com.kordia.achievements.data.mapper.toGoalDtoList
import com.kordia.achievements.domain.Constants
import com.kordia.achievements.domain.model.GoalDto
import com.kordia.achievements.domain.repository.GoalRepository
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

/**
 * Goal repository class.
 *
 * @author Mohammedsaif Kordia
 */
class GoalRepositoryImpl @Inject constructor(
    private val goalApi: GoalApi,
    private val goalDao: GoalDao,
    private val userDao: UserDao
) : GoalRepository {

    /**
     * Create a goal
     *
     * @param goal the goal to create
     * @return      a flow of created goal wrapped with DataState
     */
    override suspend fun create(goal: GoalDto) = flow {
        try {
            emit(DataState.Loading)
            val mId = UUID.randomUUID().toString()
            val user = userDao.getCurrentUser()
            if (user == null || user.id == Constants.GUEST_USER_ID) {
                val goalData =
                    goal.apply {
                        id = Constants.GUEST_USER_ID.plus("::").plus(mId)
                        userId = Constants.GUEST_USER_ID
                    }.toGoalData()
                val insert = goalDao.insert(goalData)
                if (insert == -1L)
                    emit(DataState.Error(Exception("Insert error")))
                else
                    emit(DataState.Success(goalData.toGoalDto()))
            } else {
                val goalDto =
                    goal.apply {
                        id = user.id.plus("::").plus(mId)
                        userId = user.id
                    }
                val onlineGoal =
                    goalApi.create(goalDto, "Bearer ".plus(user.token))
                val insert = goalDao.insert(onlineGoal.toGoalData())
                if (insert == -1L)
                    emit(DataState.Error(Exception("Insert error")))
                else
                    emit(DataState.Success(onlineGoal))

            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    /**
     * Get a goal by id.
     *
     * @param id the id for the goal to get
     * @return   a flow of goal with the specified id wrapped with DataState
     */
    override suspend fun get(id: String): Flow<DataState<GoalDto>> = flow {
        emit(DataState.Loading)
        try {
            val goal = goalDao.get(id)
            if (goal != null) {
                emit(DataState.Success(goal.toGoalDto()))
            } else {
                emit(DataState.Empty)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    /**
     * Get all goals.
     *
     * @return a flow of all goals wrapped with DataState
     */
    override suspend fun getAll() = flow {
        emit(DataState.Loading)
        try {
            val user = userDao.getCurrentUser()
            if (user != null) {
                val token = userDao.getCurrentUserToken()
                val onlineGoals = goalApi.getAll("Bearer $token")
                onlineGoals.toGoalDataList().forEach { goalData ->
                    goalDao.insert(goalData)
                }
                val localGoals = goalDao.getAll(user.id)
                if (localGoals.isNotEmpty()) {
                    emit(DataState.Success(localGoals.toGoalDtoList()))
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
                    val localGoals = goalDao.getAll(user.id)
                    if (localGoals.isNotEmpty()) {
                        emit(DataState.Success(localGoals.toGoalDtoList()))
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
     * Update a goal.
     *
     * @param goal the goal to update
     * @return     a flow of updated goal wrapped with DataState
     */
    override suspend fun update(goal: GoalDto): Flow<DataState<GoalDto>> = flow {
        emit(DataState.Loading)
        try {
            val user = userDao.getCurrentUser()
            if (user == null || user.id == Constants.GUEST_USER_ID) {
                val goalData = goal.toGoalData()
                goalDao.update(goalData)
                emit(DataState.Success(goalData.toGoalDto()))
            } else {
                val onlineGoal =
                    goalApi.update(goal, "Bearer ".plus(user.token))
                goalDao.update(onlineGoal.toGoalData())
                emit(DataState.Success(onlineGoal))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    /**
     * Delete a goal.
     *
     * @param id the id for the goal to delete
     * @return   a flow of deleted goal id wrapped with DataState
     */
    override suspend fun delete(id: String): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        try {
            val user = userDao.getCurrentUser()
            if (user == null || user.id == Constants.GUEST_USER_ID) {
                goalDao.delete(id)
                emit(DataState.Success(id))
            } else {
                goalApi.delete(id, "Bearer ".plus(user.token))
                goalDao.delete(id)
                emit(DataState.Success(id))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}