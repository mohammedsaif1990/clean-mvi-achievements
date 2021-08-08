package com.kordia.achievements.data.api.achievement

import com.kordia.achievements.data.api.ApiTablesNames.achievement
import com.kordia.achievements.domain.model.AchievementDto
import retrofit2.Response
import retrofit2.http.*

/**
 * AchievementApi retrofit interface.
 *
 * @author Mohammedsaif Kordia
 */
interface AchievementApi {

    /**
     * Get all achievements.
     *
     * @param token the current token to authorize the request
     * @return      all achievements
     */
    @GET(achievement)
    suspend fun getAll(
        @Header("Authorization") token: String
    ): List<AchievementDto>

    /**
     * Create an achievement.
     *
     * @param achievement the achievement to create
     * @param token       the current token to authorize the request
     * @return            the created achievement
     */
    @POST(achievement)
    suspend fun create(
        @Body achievement: AchievementDto,
        @Header("Authorization") token: String
    ): AchievementDto

    /**
     * Update an achievement.
     *
     * @param achievement the achievement to update
     * @param token       the current token to authorize the request
     * @return            the updated achievement
     */
    @PUT(achievement)
    suspend fun update(
        @Body achievement: AchievementDto,
        @Header("Authorization") token: String
    ): AchievementDto

    /**
     * Create a list of achievements.
     *
     * @param achievementList the achievements list to create
     * @param returnValue     a boolean value to tell the server if we want it to return the result
     * @param token           the current token to authorize the request
     * @return                a list of created achievements if returnValue is true
     */
    @POST("$achievement/list/{returnValue}")
    suspend fun createList(
        @Body achievementList: List<AchievementDto>,
        @Path("returnValue") returnValue: Boolean,
        @Header("Authorization") token: String
    ): List<AchievementDto>

    /**
     * Update a list of achievements.
     *
     * @param achievementList the achievements list to update
     * @param returnValue     a boolean value to tell the server if we want it to return the result
     * @param token           the current token to authorize the request
     * @return                a list of updated achievements if returnValue is true
     */
    @PUT("$achievement/list/{returnValue}")
    suspend fun updateList(
        @Body achievementList: List<AchievementDto>,
        @Path("returnValue") returnValue: Boolean,
        @Header("Authorization") token: String
    ): List<AchievementDto>

    /**
     * Get an achievement by id.
     *
     * @param id    the id for the achievement to get
     * @param token the current token to authorize the request
     * @return      the achievement with the specified id
     */
    @GET("$achievement/{id}")
    suspend fun get(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): AchievementDto

    /**
     * Get all achievements within or after a date.
     *
     * @param lastUpdated the long date to get from and after
     * @param token       the current token to authorize the request
     * @return            all achievements within or after lastUpdated
     */
    @GET("$achievement/after/{lastUpdated}")
    suspend fun getAddedUpdated(
        @Path("lastUpdated") lastUpdated: Long,
        @Header("Authorization") token: String
    ): List<AchievementDto>

    /**
     * Get all achievements before date.
     *
     * @param token the current token to authorize the request
     * @return      all achievements with lastUpdated less than 0
     */
    @GET("$achievement/before/0")
    suspend fun getDeleted(
        @Header("Authorization") token: String
    ): List<AchievementDto>

    /**
     * Delete an achievement.
     *
     * @param id    the id for the achievement to delete
     * @param token the current token to authorize the request
     */
    @DELETE("${achievement}/{id}")
    suspend fun delete(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Unit>
}