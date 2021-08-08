package com.kordia.achievements.data.repository

import com.kordia.achievements.data.api.user.UserApi
import com.kordia.achievements.data.db.user.UserDao
import com.kordia.achievements.data.db.user.UserData
import com.kordia.achievements.data.mapper.toUserData
import com.kordia.achievements.data.mapper.toUserDto
import com.kordia.achievements.domain.Constants.GUEST_USER_ID
import com.kordia.achievements.domain.model.AuthenticateRequestDto
import com.kordia.achievements.domain.model.UserDto
import com.kordia.achievements.domain.repository.AchievementRepository
import com.kordia.achievements.domain.repository.GoalRepository
import com.kordia.achievements.domain.repository.SettingRepository
import com.kordia.achievements.domain.repository.UserRepository
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * User repository class.
 *
 * @author Mohammedsaif Kordia
 */
class UserRepositoryImpl @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val goalRepository: GoalRepository,
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val settingRepository: SettingRepository,
) : UserRepository {

    /**
     * Update a user
     *
     * @param user the user to update
     * @return     a flow of updated user wrapped with DataState
     */
    override suspend fun update(user: UserDto) = flow {
        emit(DataState.Loading)
        try {

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Initiate the current user, this function will check for the current user
     * and will create a guest user if no users exists.
     *
     * @return a flow of the current user wrapped with DataState
     */
    override suspend fun initUser() = flow {
        emit(DataState.Loading)
        try {
            when (userDao.getCount()) {
                0 -> {
                    val guestUser = UserData(
                        id = GUEST_USER_ID,
                        login = "guest",
                        firstName = "guest",
                        lastName = "guest",
                        email = "guest@guest",
                        imageUrl = "",
                        activated = false,
                        token = "",
                    )
                    userDao.insert(guestUser)
                    settingRepository.updateCurrentUserId(guestUser.id).collect()
                    emit(DataState.Success(guestUser.toUserDto()))
                }
                else -> {
                    val currentUser = userDao.getCurrentUser()
                    if (currentUser != null) emit(DataState.Success(currentUser.toUserDto()))
                    else emit(DataState.Empty)
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Logout this function will change the current user to the guest user.
     *
     * @return a flow of the guest user wrapped with DataState
     */
    override suspend fun logout() = flow {
        emit(DataState.Loading)
        try {
            //TODO: Add presentation logic to ask the user if he wants to keep his data
//            settingRepository.getCurrentSetting().collect {
//                achievementDao.deleteCurrentUserAchievements(it.currentUserId)
//                goalsDao.deleteCurrentUserGoals(it.currentUserId)
//            }
            settingRepository.updateCurrentUserId("guest_user").collect()
            val user = userDao.getCurrentUser()
            if (user != null) {
                emit(DataState.Success(user.toUserDto()))
            } else {
                emit(DataState.Empty)
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Login this function will authorize the user online and will set it locally
     * if authorized successfully.
     *
     * @param authenticateRequest that contains the login credentials
     * @return                    a flow of the logged in user wrapped with DataState if authorized successfully
     */
    override suspend fun login(authenticateRequest: AuthenticateRequestDto) = flow {
        emit(DataState.Loading)
        try {
            val token = userApi.authenticate(authenticateRequest)
            val user = userApi.get("Bearer ${token.id_token}")
            userDao.insert(user.apply { this.token = token.id_token }.toUserData())
            settingRepository.updateCurrentUserId(user.id ?: "").collect()
            achievementRepository.getAll().collect()
            goalRepository.getAll().collect()
            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}