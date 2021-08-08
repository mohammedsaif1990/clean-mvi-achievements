package com.kordia.achievements.data.repository

import com.kordia.achievements.data.db.setting.SettingDao
import com.kordia.achievements.data.db.setting.SettingData
import com.kordia.achievements.data.mapper.toSettingData
import com.kordia.achievements.domain.model.SettingDto
import com.kordia.achievements.domain.repository.SettingRepository
import javax.inject.Inject
import com.kordia.achievements.data.mapper.toSettingDto
import com.kordia.achievements.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * Setting repository class.
 *
 * @author Mohammedsaif Kordia
 */
class SettingRepositoryImpl @Inject constructor(
    private val settingDao: SettingDao
) : SettingRepository {

    /**
     * Get the first and only setting.
     *
     * @return a flow of setting data wrapped with DataState
     */
    override suspend fun getCurrentSetting() = flow {
        try {
            val setting: SettingData? = settingDao.get()
            if (setting != null) {
                emit(setting.toSettingDto())
            } else {
                settingDao.insert(SettingData(0L, ""))
                val insertedSetting = settingDao.get()
                if (insertedSetting != null) {
                    emit(insertedSetting.toSettingDto())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Update a setting.
     *
     * @param setting a setting to update
     * @return        a flow of the updated setting wrapped with DataState
     */
    override suspend fun update(setting: SettingDto) = flow {
        try {
            getCurrentSetting().collect { settingDto ->
                settingDao.update(settingDto.toSettingData())
            }
            emit(DataState.Success(setting))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Update current user id
     *
     * @param mCurrentUserId a user id to update on
     * @return               a flow of the updated setting wrapped with DataState
     */
    override suspend fun updateCurrentUserId(mCurrentUserId: String) = flow {
        try {
            getCurrentSetting().collect { setting ->
                settingDao.update(setting
                    .apply { this.currentUserId = mCurrentUserId }
                    .toSettingData())
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}