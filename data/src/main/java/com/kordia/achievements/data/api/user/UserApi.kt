package com.kordia.achievements.data.api.user

import com.kordia.achievements.data.api.ApiTablesNames.account
import com.kordia.achievements.data.api.ApiTablesNames.authenticate
import com.kordia.achievements.data.api.ApiTablesNames.user
import com.kordia.achievements.domain.model.AuthenticateRequestDto
import com.kordia.achievements.domain.model.TokenDto
import com.kordia.achievements.domain.model.UserDto
import retrofit2.http.*

/**
 * UserApi retrofit interface.
 *
 * @author Mohammedsaif Kordia
 */
interface UserApi {

    /**
     * Authenticate request.
     *
     * @param authenticateRequest that contains the login credentials
     * @return                     the user token
     */
    @POST(authenticate)
    suspend fun authenticate(
        @Body authenticateRequest: AuthenticateRequestDto
    ): TokenDto

    /**
     * Update user data.
     *
     * @param user  the user data to update
     * @param token the current token to authorize the request
     * @return      the updated user
     */
    @PUT(user)
    suspend fun update(
        @Body user: UserDto,
        @Header("Authorization") token: String
    ): UserDto

    /**
     * Get a user by token.
     *
     * @param token the current token to authorize the request
     * @return      the user who own the given token
     */
    @GET(account)
    suspend fun get(
        @Header("Authorization") token: String
    ): UserDto
}