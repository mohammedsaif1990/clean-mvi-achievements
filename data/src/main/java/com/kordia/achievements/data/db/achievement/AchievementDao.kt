package com.kordia.achievements.data.db.achievement

import androidx.room.*

/**
 * AchievementDao interface.
 *
 * @author Mohammedsaif Kordia
 */
@Dao
interface AchievementDao {

    /**
     * Insert an achievement to the database.
     *
     * @param  achievement the achievement to insert
     * @return             the row number as long
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: AchievementData): Long

    /**
     * Get an achievement by id.
     *
     * @param  id an id to filter on
     * @return    the achievement for the given id, could be null
     */
    @Query("SELECT * FROM achievement_table WHERE id = :id")
    suspend fun get(id: String): AchievementData?

    /**
     * Get all achievements.
     *
     * @param  userId a user id to filter on
     * @return        all the achievements that natches the user id
     */
    @Query("SELECT * FROM achievement_table WHERE user_id = :userId")
    suspend fun getAll(userId: String): List<AchievementData>

    /**
     * Update an achievement.
     *
     * @param achievement an achievement to update
     */
    @Update
    suspend fun update(achievement: AchievementData)

    /**
     * Delete an achievement.
     *
     * @param id the id for an achievement to delete
     */
    @Query("DELETE FROM achievement_table WHERE id = :id")
    suspend fun delete(id: String)

    /**
     * Delete all achievements for user.
     *
     * @param userId the user id that we want to delete his achievements
     */
    @Query("DELETE FROM achievement_table WHERE user_id = :userId")
    suspend fun deleteCurrentUserAchievements(userId: String)

    /**
     * Get achievements count by user id.
     *
     * @param userId the user id to get count by
     * @return       the count of user achievements
     */
    @Query("SELECT COUNT(*) FROM achievement_table WHERE user_id = :userId")
    suspend fun getCountByUser(userId: String): Int
}