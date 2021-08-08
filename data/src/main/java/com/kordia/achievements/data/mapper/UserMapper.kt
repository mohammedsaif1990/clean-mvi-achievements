package com.kordia.achievements.data.mapper

import com.kordia.achievements.data.db.user.UserData
import com.kordia.achievements.domain.model.UserDto

/**
 * User mapper.
 *
 * @author Mohammedsaif Kordia
 */

/**
 * Map from UserDto to UserData.
 *
 * @return mapped UserData
 */
fun UserDto.toUserData() = UserData(
    id = id ?: "",
    activated = activated ?: false,
    email = email ?: "",
    firstName = firstName ?: "",
    imageUrl = imageUrl ?: "",
    lastName = lastName ?: "",
    login = login ?: "",
    token = token ?: "",
)

/**
 * Map from UserData to UserDto.
 *
 * @return mapped UserDto
 */
fun UserData.toUserDto() = UserDto(
    id = id,
    activated = activated,
    email = email,
    firstName = firstName,
    imageUrl = imageUrl,
    lastName = lastName,
    login = login,
    token = token,
)