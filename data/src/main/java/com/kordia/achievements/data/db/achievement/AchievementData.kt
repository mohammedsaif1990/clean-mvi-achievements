package com.kordia.achievements.data.db.achievement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Achievement data class for achievement entity.
 *
 * @author Mohammedsaif Kordia
 */
@Entity(tableName = "achievement_table")
data class AchievementData(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "date_achieved") val dateAchieved: Long,
    @ColumnInfo(name = "date_expected") val dateExpected: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "goal_id") val goalId: String,
    @ColumnInfo(name = "last_updated") val lastUpdated: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type_id") val typeId: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "was_goal") val wasGoal: Boolean,
)