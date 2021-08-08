package com.kordia.achievements.domain.repository

import com.kordia.achievements.domain.model.SettingDto
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Setting repository interface.
 *
 * @author Mohammedsaif Kordia
 */
interface SettingRepository {

    /**
     * Get the first and only setting.
     *
     * @return a flow of setting data wrapped with DataState
     */
    suspend fun getCurrentSetting(): Flow<SettingDto>

    /**
     * Update a setting.
     *
     * @param setting a setting to update
     * @return        a flow of the updated setting wrapped with DataState
     */
    suspend fun update(setting: SettingDto): Flow<DataState<SettingDto>>

    /**
     * Update current user id
     *
     * @param mCurrentUserId a user id to update on
     * @return               a flow of the updated setting wrapped with DataState
     */
    suspend fun updateCurrentUserId(mCurrentUserId: String): Flow<DataState<SettingDto>>
}