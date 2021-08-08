package com.kordia.achievements.data.api.type

import com.kordia.achievements.data.api.ApiTablesNames.type
import com.kordia.achievements.domain.model.TypeDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * TypeApi retrofit interface.
 *
 * @author Mohammedsaif Kordia
 */
interface TypeApi {

    /**
     * Get all types.
     *
     * @param token the current token to authorize the request
     * @return      all types
     */
    @GET(type)
    suspend fun getAll(
        @Header("Authorization") token: String
    ): Call<List<TypeDto>>

    /**
     * Create a type.
     *
     * @param type  the type to create
     * @param token the current token to authorize the request
     * @return      the created type
     */
    @POST(type)
    suspend fun create(
        @Body type: TypeDto,
        @Header("Authorization") token: String
    ): Call<TypeDto>

    /**
     * Update a type.
     *
     * @param type  the type to update
     * @param token the current token to authorize the request
     * @return      the updated type
     */
    @PUT(type)
    suspend fun update(
        @Body type: TypeDto,
        @Header("Authorization") token: String
    ): Call<TypeDto>

    /**
     * Create a list of types.
     *
     * @param typeList    the types list to create
     * @param returnValue a boolean value to tell the server if we want it to return the result
     * @param token       the current token to authorize the request
     * @return            a list of created types if returnValue is true
     */
    @POST("$type/list/{returnValue}")
    suspend fun createList(
        @Body typeList: List<TypeDto>,
        @Path("returnValue") returnValue: Boolean,
        @Header("Authorization") token: String
    ): Call<List<TypeDto>>

    /**
     * Update a list of types.
     *
     * @param typeList    the types list to update
     * @param returnValue a boolean value to tell the server if we want it to return the result
     * @param token       the current token to authorize the request
     * @return            a list of updated types if returnValue is true
     */
    @PUT("$type/list/{returnValue}")
    suspend fun updateList(
        @Body typeList: List<TypeDto>,
        @Path("returnValue") returnValue: Boolean,
        @Header("Authorization") token: String
    ): Call<List<TypeDto>>

    /**
     * Get an type by id.
     *
     * @param id    the id for the type to get
     * @param token the current token to authorize the request
     * @return      the type with the specified id
     */
    @GET("$type/{id}")
    suspend fun get(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<TypeDto>

    /**
     * Get all types within or after a date.
     *
     * @param lastUpdated the long date to get from and after
     * @param token       the current token to authorize the request
     * @return            all types within or after lastUpdated
     */
    @GET("$type/after/{lastUpdated}")
    suspend fun getAddedUpdated(
        @Path("lastUpdated") lastUpdated: Long,
        @Header("Authorization") token: String
    ): Call<List<TypeDto>>

    /**
     * Get all types before date.
     *
     * @param token the current token to authorize the request
     * @return      all types with lastUpdated less than 0
     */
    @GET("$type/before/0")
    suspend fun getDeleted(
        @Header("Authorization") token: String
    ): Call<List<TypeDto>>

    /**
     * Delete an type.
     *
     * @param id    the id for the type to delete
     * @param token the current token to authorize the request
     */
    @DELETE("$type/{id}")
    suspend fun delete(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<Unit>
}