package com.nadhif.hayazee.database.dao

import androidx.room.*
import com.nadhif.hayazee.common.Constant.TABLE_FAVORITES
import com.nadhif.hayazee.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteMovie(favorite: FavoriteEntity)

    @Query("SELECT * FROM $TABLE_FAVORITES WHERE type=:type")
    suspend fun getAllFavorites(type: String): List<FavoriteEntity>

    @Query("SELECT * FROM $TABLE_FAVORITES WHERE id=:id")
    suspend fun getFavoriteById(id: Int): FavoriteEntity?
}