package ir.nevercom.somu.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import ir.nevercom.somu.ViewState
import ir.nevercom.somu.model.Movie
import ir.nevercom.somu.repositories.Api
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val api: Api,
    private val movieId: Int
) : ViewModel() {
    private val _state: MutableLiveData<MovieDetailsViewState> = MutableLiveData(
        MovieDetailsViewState(ViewState.Loading())
    )
    val state: LiveData<MovieDetailsViewState> = _state

    init {
        getMovieInfo()
    }

    fun getMovieInfo() {
        _state.value = MovieDetailsViewState(ViewState.Loading())
        viewModelScope.launch {
            when (val response = api.getMovieById(movieId)) {
                is NetworkResponse.Success -> {
                    _state.value = MovieDetailsViewState(ViewState.Loaded(response.body))
                }
                is NetworkResponse.ServerError -> {
                    _state.value = MovieDetailsViewState(
                        ViewState.Error(
                            Exception(
                                response.body?.errorMessage ?: response.error.message
                            )
                        )
                    )
                }
                else -> {
                    _state.value = MovieDetailsViewState(
                        ViewState.Error(
                            Exception(
                                response.toString()
                            )
                        )
                    )
                }
            }
        }
    }
}

data class MovieDetailsViewState(
    val movie: ViewState<Movie>
)