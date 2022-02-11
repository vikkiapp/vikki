package ir.nevercom.somu.repositories

import androidx.paging.PagingSource
import ir.nevercom.somu.model.Movie

interface WatchlistRepository {
    fun add(movie: Movie)
    fun remove(id: Int)
    fun isInWatchlist(id: Int): Boolean
    fun getAll(): List<Movie>
    fun getAllPaginated(): PagingSource<Int, Movie>
}