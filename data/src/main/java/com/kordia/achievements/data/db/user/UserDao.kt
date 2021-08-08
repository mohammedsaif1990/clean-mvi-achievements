package com.kordia.achievements.data.db.user

import androidx.room.*

/**
 * UserDao interface.
 *
 * @author Mohammedsaif Kordia
 */
@Dao
interface UserDao {

    /**
     * Insert a user to the database.
     *
     * @param user the user to insert
     * @return     the row number as long
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserData): Long

    /**
     * Get the current user.
     *
     * @return the current user
     */
    @Query("SELECT U.* FROM user_table U INNER JOIN setting_table S ON U.id = S.current_user_id LIMIT 1")
    suspend fun getCurrentUser(): UserData?

    /**
     * Update a user.
     *
     * @param user a user to update
     */
    @Update
    suspend fun update(user: UserData)

    /**
     * Delete a user.
     *
     * @param id the id for a user to delete
     */
    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun delete(id: String)

    /**
     * Get current user token.
     *
     * @return the token for the current user
     */
    @Query("SELECT U.token FROM user_table U INNER JOIN setting_table S ON U.id = S.current_user_id LIMIT 1")
    suspend fun getCurrentUserToken(): String

    /**
     * Get count.
     *
     * @return the count of table records
     */
    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun getCount(): Int
}