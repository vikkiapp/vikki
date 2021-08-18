package ir.nevercom.somu.ui.screen.showDetails

import android.util.Log
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

    companion object {
        const val TAG = "ShowDetailsViewModel"
    }

    init {
        viewModelScope.launch {
            getDetails()
        }
        viewModelScope.launch {
            getCast()
        }
        viewModelScope.launch {
            getCrew()
        }
        viewModelScope.launch {
            getRatings()
        }
    }

    private suspend fun getDetails() {
        Log.d(TAG, "getDetails: ")
        _state.value = currentState().copy(details = ViewState.Loading())
        when (val response = tmdb.showService.details(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(details = ViewState.Loaded(response.body))
                getEpisodes(response.body)
            }
        }
        Log.d(TAG, "getDetails: Finished")
    }

    private suspend fun getCast() {
        Log.d(TAG, "getCast: ")
        _state.value = currentState().copy(cast = ViewState.Loading())
        when (val response = tmdb.showService.cast(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(cast = ViewState.Loaded(response.body))
            }
        }
        Log.d(TAG, "getCast: Finished")
    }

    private suspend fun getCrew() {
        Log.d(TAG, "getCrew: ")
        _state.value = currentState().copy(crew = ViewState.Loading())
        when (val response = tmdb.showService.aggregateCrew(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(crew = ViewState.Loaded(response.body))
            }
        }
        Log.d(TAG, "getCrew: Finished")
    }

    private suspend fun getRatings() {
        Log.d(TAG, "getRatings: ")
        _state.value = currentState().copy(ratings = ViewState.Loading())
        when (val response = tmdb.showService.contentRatings(showId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(ratings = ViewState.Loaded(response.body))
            }
        }
        Log.d(TAG, "getRatings: Finished")
    }

    private suspend fun getEpisodes(show: TmdbShow) {
        Log.d(TAG, "getEpisodes: ")
        _state.value = currentState().copy(episodes = ViewState.Loading())
        val list = mutableMapOf<Int, List<TmdbEpisode.Slim>>()

        val episodes = show.seasons.map {
            viewModelScope.async {
                when (val response = tmdb.seasonService.details(showId, it.seasonNumber)) {
                    is NetworkResponse.Success -> Pair(it.seasonId, response.body.episodes)
                    else -> Pair(it.seasonId, emptyList())
                }
            }
        }.awaitAll()

        episodes.forEach {
            list[it.first] = it.second
        }

        _state.value = currentState().copy(episodes = ViewState.Loaded(list))
        Log.d(TAG, "getEpisodes: Finished")
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