package com.nadhif.hayazee.domain.favorite

import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.data.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    operator fun invoke(): Flow<ResponseState<List<Movie>>> {
        return flow {
            emit(ResponseState.Loading())
            try {
                emit(favoriteRepository.getFavoriteMovies())
            } catch (e: Exception) {
                emit(ResponseState.Error(e.localizedMessage))
            }
        }
    }
}