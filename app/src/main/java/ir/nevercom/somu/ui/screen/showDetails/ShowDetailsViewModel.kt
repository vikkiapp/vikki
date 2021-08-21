package ir.nevercom.somu.ui.screen.showDetails

import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbContentRating
import de.vkay.api.tmdb.models.TmdbEpisode
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbShow
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val tmdb: TMDb
) : ViewModel() {
    private val showId: Int = handle.get("id")!!
    private val _state: MutableLiveData<ShowDetailsViewState> = MutableLiveData(
        ShowDetailsViewState.Empty
    )
    val state: LiveData<ShowDetailsViewState> = _state
    private fun currentState(): ShowDetailsViewState = _state.value!!

    init {
        getDetails()
        getCast()
        getCrew()
        getRatings()
    }

    private fun getDetails() = viewModelScope.launch {
        _state.value = currentState().copy(details = ViewState.Loading())
        when (val response = tmdb.showService.details(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(details = ViewState.Loaded(response.body))
                getEpisodes(response.body)
            }
        }
    }

    private fun getCast() = viewModelScope.launch {
        _state.value = currentState().copy(cast = ViewState.Loading())
        when (val response = tmdb.showService.cast(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(cast = ViewState.Loaded(response.body))
            }
        }
    }

    private fun getCrew() = viewModelScope.launch {
        _state.value = currentState().copy(crew = ViewState.Loading())
        when (val response = tmdb.showService.aggregateCrew(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(crew = ViewState.Loaded(response.body))
            }
        }
    }

    private fun getRatings() = viewModelScope.launch {
        _state.value = currentState().copy(ratings = ViewState.Loading())
        when (val response = tmdb.showService.contentRatings(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(ratings = ViewState.Loaded(response.body))
            }
        }
    }

    private fun getEpisodes(show: TmdbShow) = viewModelScope.launch {
        _state.value = currentState().copy(episodes = ViewState.Loading())

        val episodes = show.seasons.map {
            viewModelScope.async {
                when (val response = tmdb.seasonService.details(showId, it.seasonNumber)) {
                    is NetworkResponse.Success -> Pair(it.seasonId, response.body.episodes)
                    else -> Pair(it.seasonId, emptyList())
                }
            }
        }.awaitAll()

        _state.value = currentState().copy(episodes = ViewState.Loaded(episodes.toMap()))
    }

}

data class ShowDetailsViewState(
    val details: ViewState<TmdbShow>,
    val ratings: ViewState<List<TmdbContentRating>>,
    val cast: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>>,
    val crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    val episodes: ViewState<Map<Int, List<TmdbEpisode.Slim>>>,
) {
    companion object {
        val Empty = ShowDetailsViewState(
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
        )
    }
}