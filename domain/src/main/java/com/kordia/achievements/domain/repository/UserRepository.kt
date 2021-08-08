package com.kordia.achievements.domain.repository

import com.kordia.achievements.domain.model.AuthenticateRequestDto
import com.kordia.achievements.domain.model.UserDto
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * User repository interface.
 *
 * @author Mohammedsaif Kordia
 */
interface UserRepository {

    /**
     * Update a user
     *
     * @param user the user to update
     * @return     a flow of updated user wrapped with DataState
     */
    suspend fun update(user: UserDto): Flow<DataState<UserDto>>

    /**
     * Initiate the current user, this function will check for the current user
     * and will create a guest user if no users exists.
     *
     * @return a flow of the current user wrapped with DataState
     */
    suspend fun initUser(): Flow<DataState<UserDto>>

    /**
     * Logout this function will change the current user to the guest user.
     *
     * @return a flow of the guest user wrapped with DataState
     */
    suspend fun logout(): Flow<DataState<UserDto>>

    /**
     * Login this function will authorize the user online and will set it locally
     * if authorized successfully.
     *
     * @param authenticateRequest that contains the login credentials
     * @return                    a flow of the logged in user wrapped with DataState if authorized successfully
     */
    suspend fun login(authenticateRequest: AuthenticateRequestDto): Flow<DataState<UserDto>>
}