package ir.nevercom.somu.ui.screen.showDetails

import android.util.Log
import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbContentRating
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbShow
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val tmdb: TMDb
) : ViewModel() {
    private val showId: Int = handle.get("id")!!
    private val _state: MutableLiveData<ShowDetailsViewState> = MutableLiveData(
        ShowDetailsViewState.Empty
    )
    val state: LiveData<ShowDetailsViewState> = _state
    private fun currentState(): ShowDetailsViewState = _state.value!!

    init {
        viewModelScope.launch {
            getDetails()
            getCast()
            getCrew()
            getRatings()
        }
    }

    private suspend fun getDetails() {
        _state.value = currentState().copy(details = ViewState.Loading())
        when (val response = tmdb.showService.details(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(details = ViewState.Loaded(response.body))
            }
        }
    }

    private suspend fun getCast() {
        _state.value = currentState().copy(cast = ViewState.Loading())
        when (val response = tmdb.showService.cast(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(cast = ViewState.Loaded(response.body))
            }
        }
    }

    private suspend fun getCrew() {
        _state.value = currentState().copy(crew = ViewState.Loading())
        val response = tmdb.showService.aggregateCrew(showId)
        Log.d("Show", response.toString())
        when (response) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(crew = ViewState.Loaded(response.body))
            }
        }
    }

    private suspend fun getRatings() {
        _state.value = currentState().copy(ratings = ViewState.Loading())
        when (val response = tmdb.showService.contentRatings(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(ratings = ViewState.Loaded(response.body))
            }
        }
    }

}

data class ShowDetailsViewState(
    val details: ViewState<TmdbShow>,
    val ratings: ViewState<List<TmdbContentRating>>,
    val cast: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>>,
    val crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
) {
    companion object {
        val Empty = ShowDetailsViewState(
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
        )
    }
}