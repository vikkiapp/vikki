package ir.nevercom.somu.ui.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.MediaTypeItem
import de.vkay.api.tmdb.models.TmdbPage
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val tmdb: TMDb) : ViewModel() {
    private val _state: MutableLiveData<SearchViewState> =
        MutableLiveData(SearchViewState(ViewState.Empty()))

    val state: LiveData<SearchViewState> = _state

    fun search(query: String, page: Int = 1) = viewModelScope.launch {
        _state.value = SearchViewState(ViewState.Loading())

        when (val response = tmdb.searchService.multi(query = query, page = page)) {
            is NetworkResponse.Success -> {
                _state.value = SearchViewState(ViewState.Loaded(response.body))
            }
        }
    }
}

data class SearchViewState(
    val result: ViewState<TmdbPage<MediaTypeItem>>
)