package com.kordia.achievements.data.db.goal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Goal data class for goal entity.
 *
 * @author Mohammedsaif Kordia
 */
@Entity(tableName = "goal_table")
data class GoalData(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id : String,
    @ColumnInfo(name = "achieved") val achieved : Boolean,
    @ColumnInfo(name = "date_achieved") val dateAchieved : Long,
    @ColumnInfo(name = "date_created") val dateCreated : Long,
    @ColumnInfo(name = "date_expected") val dateExpected : Long,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "last_updated") val lastUpdated : Long,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "type_id") val typeId : String,
    @ColumnInfo(name = "user_id") val userId : String,
)
