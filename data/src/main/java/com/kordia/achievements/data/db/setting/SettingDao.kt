package com.kordia.achievements.data.db.setting

import androidx.room.*

/**
 * SettingDao interface.
 *
 * @author Mohammedsaif Kordia
 */
@Dao
interface SettingDao {

    /**
     * Insert a setting to the database.
     *
     * @param  setting the setting to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: SettingData)

    /**
     * Get the first and only setting.
     *
     * @return setting data, could be null
     */
    @Query("SELECT * FROM setting_table WHERE id = 0")
    suspend fun get(): SettingData?

    /**
     * Update a setting.
     *
     * @param setting a setting to update
     */
    @Update
    suspend fun update(setting: SettingData)

    /**
     * Get the current user id from setting.
     *
     * @return the current user id
     */
    @Query("SELECT current_user_id FROM setting_table WHERE id = 0")
    suspend fun getCurrentUserId(): String

    /**
     * Get count.
     *
     * @return the count of table records
     */
    @Query("SELECT COUNT(*) FROM setting_table")
    suspend fun getCount(): Int
}