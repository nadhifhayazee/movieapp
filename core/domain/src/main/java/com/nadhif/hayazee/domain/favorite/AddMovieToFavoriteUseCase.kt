package com.nadhif.hayazee.domain.favorite

import android.util.Log
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.data.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    operator fun invoke(movie: Movie): Flow<ResponseState<Boolean>> {
        return flow {
            try {
                when (favoriteRepository.getFavoriteByID(movie.id ?: 0)) {
                    is ResponseState.SuccessWithData -> {
                        favoriteRepository.deleteFavoriteMovie(movie)
                        emit(
                            ResponseState.Success()
                        )
                    }
                    else -> {
                        favoriteRepository.insertFavoriteMovie(movie)
                        emit(ResponseState.SuccessWithData(true))
                    }
                }

            } catch (e: Exception) {
                emit(ResponseState.Error(e.localizedMessage))
            }
        }
    }
}