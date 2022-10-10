package com.nadhif.hayazee.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadhif.hayazee.database.dao.FavoriteDao
import com.nadhif.hayazee.database.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}