package com.kordia.achievements.data.db.goal

import androidx.room.*

/**
 * GoalDao interface.
 *
 * @author Mohammedsaif Kordia
 */
@Dao
interface GoalDao {

    /**
     * Insert a goal to the database.
     *
     * @param  goal the goal to insert
     * @return the row number as long
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: GoalData): Long

    /**
     * Get a goal by id.
     *
     * @param  id an id to filter on
     * @return    the goal for the given id, could be null
     */
    @Query("SELECT * FROM goal_table WHERE id = :id")
    suspend fun get(id: String): GoalData?

    /**
     * Get all goals.
     *
     * @param  userId a user id to filter on
     * @return        all the goals that natches the user id
     */
    @Query("SELECT * FROM goal_table WHERE user_id = :userId")
    suspend fun getAll(userId: String): List<GoalData>

    /**
     * Update a goal.
     *
     * @param goal a goal to update
     */
    @Update
    suspend fun update(goal: GoalData)

    /**
     * Delete a goal.
     *
     * @param id the id for a goal to delete
     */
    @Query("DELETE FROM goal_table WHERE id = :id")
    suspend fun delete(id: String)

    /**
     * Delete all goals for user.
     *
     * @param userId the user id that we want to delete his goals
     */
    @Query("DELETE FROM goal_table WHERE user_id = :userId")
    suspend fun deleteCurrentUserGoals(userId: String)

    /**
     * Get achieved goals count by user id.
     *
     * @param userId the user id to get count by
     * @return       the count of user achieved goals
     */
    @Query("SELECT COUNT(*) FROM goal_table WHERE user_id = :userId AND achieved")
    suspend fun getAchievedCountByUser(userId: String): Int

    /**
     * Get goals count by user id.
     *
     * @param userId the user id to get count by
     * @return       the count of user goals
     */
    @Query("SELECT COUNT(*) FROM goal_table WHERE user_id = :userId")
    suspend fun getGoalsCountByUser(userId: String): Int
}