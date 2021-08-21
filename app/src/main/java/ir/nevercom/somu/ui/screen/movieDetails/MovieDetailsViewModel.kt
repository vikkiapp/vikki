package ir.nevercom.somu.ui.screen.movieDetails

import android.util.Log
import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbReleaseDate
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val tmdb: TMDb
) : ViewModel() {
    private val movieId: Int = handle.get("id")!!
    private val _state: MutableLiveData<MovieDetailsViewState> = MutableLiveData(
        MovieDetailsViewState.Empty
    )
    val state: LiveData<MovieDetailsViewState> = _state
    private fun currentState(): MovieDetailsViewState = _state.value!!

    init {
        getMovieInfo()
        getCast()
        getCrew()
        getCertifications()
    }

    private fun getMovieInfo() = viewModelScope.launch {
        _state.value = currentState().copy(movie = ViewState.Loading())
        when (val response = tmdb.movieService.details(movieId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(movie = ViewState.Loaded(response.body))
            }
        }

    }

    private fun getCast() = viewModelScope.launch {
        _state.value = currentState().copy(cast = ViewState.Loading())
        when (val response = tmdb.movieService.cast(movieId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(cast = ViewState.Loaded(response.body))
            }
        }

    }

    private fun getCrew() = viewModelScope.launch {
        _state.value = currentState().copy(crew = ViewState.Loading())
        when (val response = tmdb.movieService.crew(movieId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(crew = ViewState.Loaded(response.body))
            }
        }

    }

    private fun getCertifications() = viewModelScope.launch {
        _state.value = currentState().copy(releaseDates = ViewState.Loading())
        when (val response = tmdb.movieService.releaseDates(movieId)) {
            is NetworkResponse.Success -> {
                _state.value =
                    currentState().copy(releaseDates = ViewState.Loaded(response.body))
            }
            else -> Log.d("getCertifications error", response.toString())
        }

    }
}

data class MovieDetailsViewState(
    val movie: ViewState<TmdbMovie>,
    val cast: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>>,
    val crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    val releaseDates: ViewState<Map<String, List<TmdbReleaseDate>>>,
) {
    companion object {
        val Empty = MovieDetailsViewState(
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
        )
    }
}