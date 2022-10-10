package com.nadhif.hayazee.database.module

import android.content.Context
import androidx.room.Room
import com.nadhif.hayazee.common.Constant
import com.nadhif.hayazee.database.dao.FavoriteDao
import com.nadhif.hayazee.database.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        Constant.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideFavoriteDao(
        db: MovieDatabase
    ): FavoriteDao = db.favoriteDao()
}