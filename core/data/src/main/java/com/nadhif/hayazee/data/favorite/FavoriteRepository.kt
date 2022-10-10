package com.nadhif.hayazee.data.favorite

import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState

interface FavoriteRepository {

    suspend fun getFavoriteMovies(): ResponseState<List<Movie>>
    suspend fun getFavoriteTvs(): ResponseState<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie): ResponseState<Boolean>
    suspend fun insertFavoriteTv(movie: Movie): ResponseState<Boolean>

    suspend fun getFavoriteByID(id: Int): ResponseState<Movie>

    suspend fun deleteFavoriteMovie(movie: Movie): ResponseState<Boolean>
    suspend fun deleteFavoriteTv(movie: Movie): ResponseState<Boolean>


}