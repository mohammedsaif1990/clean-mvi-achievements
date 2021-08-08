package com.kordia.achievements.data.mapper

import com.kordia.achievements.data.db.achievement.AchievementData
import com.kordia.achievements.domain.model.AchievementDto

/**
 * Achievement mapper.
 *
 * @author Mohammedsaif Kordia
 */

/**
 * Map from AchievementDto to AchievementData.
 *
 * @return mapped AchievementData
 */
fun AchievementDto.toAchievementData() = AchievementData(
    id = id ?: "",
    dateAchieved = dateAchieved ?: 0L,
    dateExpected = dateExpected ?: 0L,
    description = description ?: "",
    goalId = goalId ?: "",
    lastUpdated = lastUpdated ?: 0L,
    title = title ?: "",
    typeId = typeId ?: "",
    userId = userId ?: "",
    wasGoal = wasGoal ?: false
)

/**
 * Map from AchievementData to AchievementDto.
 *
 * @return mapped AchievementDto
 */
fun AchievementData.toAchievementDto() = AchievementDto(
    id = id,
    dateAchieved = dateAchieved,
    dateExpected = dateExpected,
    description = description,
    goalId = goalId,
    lastUpdated = lastUpdated,
    title = title,
    typeId = typeId,
    userId = userId,
    wasGoal = wasGoal,
)

/**
 * Map from List<AchievementDto> to List<AchievementData>
 * @return mapped List<AchievementData>
 */
fun List<AchievementDto>.toAchievementDataList() = this.map { it.toAchievementData() }

/**
 * Map from List<AchievementData> to List<AchievementDto>
 * @return mapped List<AchievementDto>
 */
fun List<AchievementData>.toAchievementDtoList() = this.map { it.toAchievementDto() }