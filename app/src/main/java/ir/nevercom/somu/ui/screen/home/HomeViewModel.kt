package ir.nevercom.somu.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbMovie
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: Api, private val tmdb: TMDb) :
    ViewModel() {
    private val _state: MutableLiveData<HomeViewState> =
        MutableLiveData(HomeViewState(ViewState.Loading()))
    val state: LiveData<HomeViewState> = _state

    init {
        getList()
    }

//    private fun search() {
//        _state.value = HomeViewState(ViewState.Loading())
//        viewModelScope.launch {
//            when (val response = api.search("god")) {
//                is NetworkResponse.Success -> {
//                    _state.value = HomeViewState(ViewState.Loaded(response.body.results))
//                }
//                is NetworkResponse.ServerError -> {
//                    _state.value = HomeViewState(
//                        ViewState.Error(
//                            Throwable(
//                                response.body?.errorMessage ?: "Something went wrong"
//                            )
//                        )
//                    )
//                }
//                else -> {
//                    _state.value = HomeViewState(
//                        ViewState.Error(
//                            Throwable("Something went wrong")
//                        )
//                    )
//                }
//            }
//        }
//    }

    private fun getList() = viewModelScope.launch {
        when (val response = tmdb.movieService.popular()) {
            is NetworkResponse.Success -> {
                _state.value = HomeViewState(ViewState.Loaded(response.body.results))
            }
        }
    }
}

/*
inline fun <T : Any, R : Any> execute(
    input: NetworkResponse<T, TmdbError.DefaultError>,
    crossinline result: (response: NetworkResponse.Success<T>) -> R
) = when (input) {
    is NetworkResponse.Success -> {
        ViewState.Loaded(result(input))
    }
    is NetworkResponse.ServerError -> {
        ViewState.Error(Throwable(input.body?.message ?: "Something went wrong"))
    }
    else -> ViewState.Error(Throwable("Something went wrong"))
}
 */
data class HomeViewState(
    val movies: ViewState<List<TmdbMovie.Slim>>
)