package com.kordia.achievements.data.db.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Type data class for type entity.
 *
 * @author Mohammedsaif Kordia
 */
@Entity(tableName = "type_table")
data class TypeData(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "image") val image: String,
)
