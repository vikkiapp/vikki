package ir.nevercom.somu.ui.screen.discover

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.Discover
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPage
import de.vkay.api.tmdb.models.TmdbShow
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val tmdb: TMDb
) : ViewModel() {
    val title: String = handle.get("title") ?: "Discover Movies"
    private val discoverSource = DiscoverSource(tmdb, DiscoverOptions.options)
    val movies: Flow<PagingData<TmdbMovie.Slim>> = Pager(PagingConfig(pageSize = 20)) {
        discoverSource
    }.flow


    private val _state: MutableLiveData<DiscoverScreenViewState> = MutableLiveData(
        DiscoverScreenViewState.Empty
    )
    val state: LiveData<DiscoverScreenViewState> = _state
    private fun currentState(): DiscoverScreenViewState = _state.value!!

    fun refresh(options: Discover.MovieBuilder) = viewModelScope.launch {
        _state.value = currentState().copy(movies = ViewState.Loading())
        when (val response = tmdb.discoverService.movie(options)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(movies = ViewState.Loaded(response.body))
            }
        }
    }
}

data class DiscoverScreenViewState(
    val movies: ViewState<TmdbPage<TmdbMovie.Slim>>,
    val shows: ViewState<TmdbPage<TmdbShow.Slim>>,
) {
    companion object {
        val Empty = DiscoverScreenViewState(
            ViewState.Empty(),
            ViewState.Empty(),
        )
    }
}