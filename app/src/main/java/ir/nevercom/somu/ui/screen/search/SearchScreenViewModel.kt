package ir.nevercom.somu.ui.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPage
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val tmdb: TMDb) : ViewModel() {
    private val _state: MutableLiveData<SearchViewState> =
        MutableLiveData(SearchViewState(ViewState.Empty()))

    val state: LiveData<SearchViewState> = _state

    fun search(query: String, page: Int = 1) {
        if (_state.value?.movies is ViewState.Empty) {
            _state.value = SearchViewState(ViewState.Loading())
        }
        viewModelScope.launch {
            when (val response = tmdb.searchService.movie(query = query, page = page)) {
                is NetworkResponse.Success -> {
                    _state.value = SearchViewState(ViewState.Loaded(response.body))
                }
            }
        }
    }
}

data class SearchViewState(
    val movies: ViewState<TmdbPage<TmdbMovie.Slim>>
)