package com.kordia.achievements.data.db.setting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Setting data class for setting entity.
 *
 * @author Mohammedsaif Kordia
 */
@Entity(tableName = "setting_table")
data class SettingData(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) var id: Long,
    @ColumnInfo(name = "current_user_id") var currentUserId: String
)
