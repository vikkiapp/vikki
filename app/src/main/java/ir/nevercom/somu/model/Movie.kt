package ir.nevercom.somu.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import de.vkay.api.tmdb.models.TmdbDate
import de.vkay.api.tmdb.models.TmdbImage

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val backdrop: TmdbImage?,
    val poster: TmdbImage?,
    val originalLanguage: String,
    val originalTitle: String,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: TmdbDate?,
    val adult: Boolean,
    val popularity: Double,
    val voteCount: Int,
    val isLiked: Boolean,
    val isInWatchlist: Boolean,
    val isWatched: Boolean,
)

class Converters {

    //region TmdbDate
    @TypeConverter
    fun dateFromString(value: String?): TmdbDate? {
        return value?.let { TmdbDate(it) }
    }

    @TypeConverter
    fun dateToString(date: TmdbDate?): String? {
        return date?.toString()
    }
    //endregion

    //region TmdbImage
    @TypeConverter
    fun imageFromString(value: String?): TmdbImage? {
        return value?.let { TmdbImage(it) }
    }

    @TypeConverter
    fun imageToString(image: TmdbImage?): String? {
        return image?.filePath
    }
    //endregion
}