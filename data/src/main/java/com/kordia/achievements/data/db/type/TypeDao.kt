package com.kordia.achievements.data.db.type

import androidx.room.*

/**
 * TypeDao interface.
 *
 * @author Mohammedsaif Kordia
 */
@Dao
interface TypeDao {

    /**
     * Insert a type to the database.
     *
     * @param  type the type to insert
     * @return the row number as long
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(type: TypeData): Long

    /**
     * Get a type by id.
     *
     * @param  id an id to filter on
     * @return    the type for the given id, could be null
     */
    @Query("SELECT * FROM type_table WHERE id = :id")
    suspend fun get(id: String): TypeData?

    /**
     * Get all types.
     *
     * @return all the types
     */
    @Query("SELECT * FROM type_table")
    suspend fun getAll(): List<TypeData>

    /**
     * Update a type.
     *
     * @param type a type to update
     */
    @Update
    suspend fun update(type: TypeData)

    /**
     * Delete a type.
     *
     * @param id the id for a type to delete
     */
    @Query("DELETE FROM type_table WHERE id = :id")
    suspend fun delete(id: String)
}