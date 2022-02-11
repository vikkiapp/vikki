package ir.nevercom.somu.repositories.local

import androidx.paging.PagingSource
import androidx.room.*
import ir.nevercom.somu.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = :id LIMIT 1")
    fun getItemById(id: Int): Movie

    @Query("SELECT * FROM Movie WHERE isInWatchlist = 1")
    fun getAllInWatchlist(): List<Movie>

    @Query("SELECT * FROM Movie WHERE isInWatchlist = 1")
    fun getAllInWatchlistPaginated(): PagingSource<Int, Movie>

    @Query("SELECT * FROM Movie WHERE isInWatchlist = 1")
    fun getAllInWatchlistFlow(): Flow<List<Movie>>

    @Query("UPDATE Movie SET isInWatchlist = :status WHERE id = :id")
    fun updateWatchlistStatus(id: Int, status: Boolean)
}