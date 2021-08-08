package com.kordia.achievements.data.api.goal

import com.kordia.achievements.data.api.ApiTablesNames.goal
import com.kordia.achievements.domain.model.GoalDto
import retrofit2.Response
import retrofit2.http.*

/**
 * GoalApi retrofit interface.
 *
 * @author Mohammedsaif Kordia
 */
interface GoalApi {

    /**
     * Get all goals.
     *
     * @param token the current token to authorize the request
     * @return      all goals
     */
    @GET(goal)
    suspend fun getAll(
        @Header("Authorization") token: String
    ): List<GoalDto>

    /**
     * Create a goal.
     *
     * @param goal  the goal to create
     * @param token the current token to authorize the request
     * @return      the created goal
     */
    @POST(goal)
    suspend fun create(
        @Body goal: GoalDto,
        @Header("Authorization") token: String
    ): GoalDto

    /**
     * Update a goal.
     *
     * @param goal  the goal to update
     * @param token the current token to authorize the request
     * @return      the updated goal
     */
    @PUT(goal)
    suspend fun update(
        @Body goal: GoalDto,
        @Header("Authorization") token: String
    ): GoalDto

    /**
     * Create a list of goals.
     *
     * @param goalList    the goals list to create
     * @param returnValue a boolean value to tell the server if we want it to return the result
     * @param token       the current token to authorize the request
     * @return            a list of created goals if returnValue is true
     */
    @POST("$goal/list/{returnValue}")
    suspend fun createList(
        @Body goalList: List<GoalDto>,
        @Path("returnValue") returnValue: Boolean,
        @Header("Authorization") token: String
    ): List<GoalDto>

    /**
     * Update a list of goals.
     *
     * @param goalList    the goals list to update
     * @param returnValue a boolean value to tell the server if we want it to return the result
     * @param token       the current token to authorize the request
     * @return            a list of updated goals if returnValue is true
     */
    @PUT("$goal/list/{returnValue}")
    suspend fun updateList(
        @Body goalList: List<GoalDto>,
        @Path("returnValue") returnValue: Boolean,
        @Header("Authorization") token: String
    ): List<GoalDto>

    /**
     * Get an goal by id.
     *
     * @param id    the id for the goal to get
     * @param token the current token to authorize the request
     * @return      the goal with the specified id
     */
    @GET("$goal/{id}")
    suspend fun get(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): GoalDto

    /**
     * Get all goals within or after a date.
     *
     * @param lastUpdated the long date to get from and after
     * @param token       the current token to authorize the request
     * @return            all goals within or after lastUpdated
     */
    @GET("$goal/after/{lastUpdated}")
    suspend fun getAddedUpdated(
        @Path("lastUpdated") lastUpdated: Long,
        @Header("Authorization") token: String
    ): List<GoalDto>

    /**
     * Get all goals before date.
     *
     * @param token the current token to authorize the request
     * @return      all goals with lastUpdated less than 0
     */
    @GET("$goal/before/0")
    suspend fun getDeleted(
        @Header("Authorization") token: String
    ): List<GoalDto>

    /**
     * Delete an goal.
     *
     * @param id    the id for the goal to delete
     * @param token the current token to authorize the request
     */
    @DELETE("$goal/{id}")
    suspend fun delete(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Unit>
}