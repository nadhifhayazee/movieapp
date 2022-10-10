package com.nadhif.hayazee.common

import androidx.core.net.toUri

object Constant {
    const val MOVIE_DATA = "MOVIE_DATA"
    const val MOVIE_ID = "MOVIE_ID"

    const val TABLE_FAVORITES = "favorites"
    const val DATABASE_NAME = "movie_db"

    fun geDetailMovieDeeplink(id: String) =
        "android-app://com.nadhif.movieapp/detail_movie/$id".toUri()

    fun geDetailTvDeeplink(id: String) =
        "android-app://com.nadhif.movieapp/detail_tv/$id".toUri()


}