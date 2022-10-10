package com.nadhif.hayazee.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nadhif.hayazee.common.Constant
import com.nadhif.hayazee.common.enums.FavoriteType
import com.nadhif.hayazee.common.model.Movie

@Entity(tableName = Constant.TABLE_FAVORITES)
data class FavoriteEntity(
    @PrimaryKey
    val id: Int? = 0,
    @ColumnInfo(name = "type")
    val type: String = FavoriteType.MOVIE.name,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "overview")
    val overview: String? = null,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = null
) {
    companion object {
        fun mapFromMovie(movie: Movie): FavoriteEntity {
            return FavoriteEntity(
                id = movie.id,
                type = FavoriteType.MOVIE.name,
                posterPath = movie.poster_path,
                title = movie.title,
                overview = movie.overview,
                voteAverage = movie.vote_average,
                backdropPath = movie.backdrop_path
            )
        }

        fun mapFromTv(tv: Movie): FavoriteEntity {
            return FavoriteEntity(
                id = tv.id,
                type = FavoriteType.TV.name,
                posterPath = tv.poster_path,
                title = tv.name,
                overview = tv.overview,
                voteAverage = tv.vote_average,
                backdropPath = tv.backdrop_path
            )
        }

        fun mapToMovie(favoriteEntity: FavoriteEntity): Movie {
            return Movie(
                id = favoriteEntity.id,
                title = favoriteEntity.title,
                overview = favoriteEntity.overview,
                vote_average = favoriteEntity.voteAverage,
                poster_path = favoriteEntity.posterPath,
                backdrop_path = favoriteEntity.backdropPath
            )
        }
    }
}