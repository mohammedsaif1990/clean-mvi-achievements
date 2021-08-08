package com.kordia.achievements.data.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User data class for user entity.
 *
 * @author Mohammedsaif Kordia
 */
@Entity(tableName = "user_table")
data class UserData(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "activated") val activated: Boolean,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "token") val token: String,
)
