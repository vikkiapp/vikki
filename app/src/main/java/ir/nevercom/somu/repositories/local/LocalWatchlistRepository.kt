package ir.nevercom.somu.repositories.local

import ir.nevercom.somu.model.Movie
import ir.nevercom.somu.repositories.WatchlistRepository
import javax.inject.Inject

class LocalWatchlistRepository @Inject constructor(
    private val dao: MovieDao
) : WatchlistRepository {
    override fun add(movie: Movie) = dao.add(movie.copy(isInWatchlist = true))

    override fun remove(id: Int) = dao.updateWatchlistStatus(id, false)

    override fun isInWatchlist(id: Int) = dao.getAllInWatchlist().find { it.id == id } != null

    override fun getAll() = dao.getAllInWatchlist()

    override fun getAllPaginated() = dao.getAllInWatchlistPaginated()
}