package ir.nevercom.somu.ui.screen.person

import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.MediaTypeItem
import de.vkay.api.tmdb.models.TmdbExternalIds
import de.vkay.api.tmdb.models.TmdbPerson
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonScreenViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val tmdb: TMDb
) : ViewModel() {
    private val personId: Int = handle.get("id")!!
    private val _state: MutableLiveData<PersonViewState> = MutableLiveData(
        PersonViewState.Empty
    )
    val state: LiveData<PersonViewState> = _state
    private fun currentState(): PersonViewState = _state.value!!

    init {
        getDetails()
        getExternalIds()
        getMoviesCast()
        getMoviesCrew()
    }

    private fun getDetails() = viewModelScope.launch {
        _state.value = currentState().copy(details = ViewState.Loading())
        when (val response = tmdb.personService.details(personId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(details = ViewState.Loaded(response.body))
            }
        }
    }

    private fun getExternalIds() = viewModelScope.launch {
        _state.value = currentState().copy(externalIds = ViewState.Loading())
        when (val response = tmdb.personService.externalIds(personId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(externalIds = ViewState.Loaded(response.body))
            }
        }
    }

    private fun getMoviesCast() = viewModelScope.launch {
        _state.value = currentState().copy(filmsCast = ViewState.Loading())
        when (val response = tmdb.personService.combinedCast(personId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(filmsCast = ViewState.Loaded(response.body))
            }
        }

    }

    private fun getMoviesCrew() = viewModelScope.launch {
        _state.value = currentState().copy(filmsCrew = ViewState.Loading())
        when (val response = tmdb.personService.combinedCrew(personId)) {
            is NetworkResponse.Success -> {
                _state.value = currentState().copy(filmsCrew = ViewState.Loaded(response.body))
            }
        }

    }
}

data class PersonViewState(
    val details: ViewState<TmdbPerson>,
    val externalIds: ViewState<TmdbExternalIds>,
    val filmsCast: ViewState<List<Pair<MediaTypeItem, TmdbPerson.CastRole>>>,
    val filmsCrew: ViewState<List<Pair<MediaTypeItem, TmdbPerson.CrewJob>>>,
) {
    companion object {
        val Empty = PersonViewState(
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
            ViewState.Empty(),
        )
    }
}