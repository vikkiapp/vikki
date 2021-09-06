package ir.nevercom.somu.ui.screen.discover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.Discover
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val tmdb: TMDb
) : ViewModel() {
    val title: String = handle.get("title") ?: "Discover Movies"
    private lateinit var _movies: Flow<PagingData<TmdbMovie.Slim>>
    val movies get() = _movies

    init {
        refresh(DiscoverOptions.options)
    }

    fun refresh(options: Discover.MovieBuilder?) = viewModelScope.launch {
        _movies = Pager(PagingConfig(pageSize = 20)) { DiscoverSource(tmdb, options) }
            .flow
            .cachedIn(viewModelScope)
    }
}
