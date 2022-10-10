package com.nadhif.hayazee.data.favorite

import com.nadhif.hayazee.common.enums.FavoriteType
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.database.dao.FavoriteDao
import com.nadhif.hayazee.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun getFavoriteMovies(): ResponseState<List<Movie>> {
        val movies = favoriteDao.getAllFavorites(FavoriteType.MOVIE.name)
        return if (movies.isNotEmpty()) {
            ResponseState.SuccessWithData(movies.map { FavoriteEntity.mapToMovie(it) })
        } else {
            ResponseState.Success()
        }
    }

    override suspend fun getFavoriteTvs(): ResponseState<List<Movie>> {
        val tv = favoriteDao.getAllFavorites(FavoriteType.TV.name)
        return if (tv.isNotEmpty()) {
            ResponseState.SuccessWithData(tv.map { FavoriteEntity.mapToMovie(it) })
        } else {
            ResponseState.Success()
        }
    }

    override suspend fun insertFavoriteMovie(movie: Movie): ResponseState<Boolean> {
        favoriteDao.insertMovie(FavoriteEntity.mapFromMovie(movie))
        return ResponseState.SuccessWithData(true)
    }

    override suspend fun insertFavoriteTv(movie: Movie): ResponseState<Boolean> {
        favoriteDao.insertMovie(FavoriteEntity.mapFromTv(movie))
        return ResponseState.SuccessWithData(true)
    }

    override suspend fun getFavoriteByID(id: Int): ResponseState<Movie> {
        val movie = favoriteDao.getFavoriteById(id)
        return if (movie != null) {
            ResponseState.SuccessWithData(FavoriteEntity.mapToMovie(movie))
        } else {
            ResponseState.Success()
        }
    }

    override suspend fun deleteFavoriteMovie(movie: Movie): ResponseState<Boolean> {

        favoriteDao.deleteMovie(FavoriteEntity.mapFromMovie(movie))
        return ResponseState.SuccessWithData(true)
    }

    override suspend fun deleteFavoriteTv(movie: Movie): ResponseState<Boolean> {
        favoriteDao.deleteMovie(FavoriteEntity.mapFromTv(movie))
        return ResponseState.SuccessWithData(true)
    }

}