package com.kordia.achievements.data.mapper

import com.kordia.achievements.data.db.setting.SettingData
import com.kordia.achievements.domain.model.SettingDto

/**
 * Setting mapper.
 *
 * @author Mohammedsaif Kordia
 */

/**
 * Map from SettingDto to SettingData.
 *
 * @return mapped SettingData
 */
fun SettingDto.toSettingData() = SettingData(id = id, currentUserId = currentUserId)

/**
 * Map from SettingData to SettingDto.
 *
 * @return mapped SettingDto
 */
fun SettingData.toSettingDto() = SettingDto(id = id, currentUserId = currentUserId)