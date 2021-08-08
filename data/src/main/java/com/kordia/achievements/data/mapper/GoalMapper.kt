package com.kordia.achievements.data.mapper

import com.kordia.achievements.data.db.goal.GoalData
import com.kordia.achievements.domain.model.GoalDto

/**
 * Goal mapper
 *
 * @author Mohammedsaif Kordia.
 */

/**
 * Map from GoalDto to GoalData.
 *
 * @return mapped GoalData
 */
fun GoalDto.toGoalData() = GoalData(
    id = id ?: "",
    achieved = achieved ?: false,
    dateAchieved = dateAchieved ?: 0L,
    dateCreated = dateCreated ?: 0L,
    dateExpected = dateExpected ?: 0L,
    description = description ?: "",
    lastUpdated = lastUpdated ?: 0L,
    title = title ?: "",
    typeId = typeId ?: "",
    userId = userId ?: "",
)

/**
 * Map from GoalData to GoalDto.
 *
 * @return mapped GoalDto
 */
fun GoalData.toGoalDto() = GoalDto(
    id = id,
    achieved = achieved,
    dateAchieved = dateAchieved,
    dateCreated = dateCreated,
    dateExpected = dateExpected,
    description = description,
    lastUpdated = lastUpdated,
    title = title,
    typeId = typeId,
    userId = userId,
)

/**
 * Map from List<GoalDto> to List<GoalData>.
 *
 * @return mapped List<GoalData>
 */
fun List<GoalDto>.toGoalDataList() = this.map { it.toGoalData() }

/**
 * Map from List<GoalData> to List<GoalDto>.
 *
 * @return mapped List<GoalDto>
 */
fun List<GoalData>.toGoalDtoList() = this.map { it.toGoalDto() }