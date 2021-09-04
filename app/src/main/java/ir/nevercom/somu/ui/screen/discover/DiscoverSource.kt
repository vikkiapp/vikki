package ir.nevercom.somu.ui.screen.discover

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.Discover
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbMovie

class DiscoverSource(
    private val tmdb: TMDb,
    private val options: Discover.MovieBuilder?
) : PagingSource<Int, TmdbMovie.Slim>() {
    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, TmdbMovie.Slim> {
        val pageNumber = params.key ?: 1
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        var nextKey: Int? = null
        val items = when (val response = tmdb.discoverService.movie(
            options = options ?: Discover.MovieBuilder(),
            page = pageNumber
        )) {
            is NetworkResponse.Success -> {
                nextKey = if (response.body.hasNextPage) pageNumber + 1 else null
                response.body.results
            }
            else -> emptyList()
        }

        return PagingSource.LoadResult.Page(
            data = items,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, TmdbMovie.Slim>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}