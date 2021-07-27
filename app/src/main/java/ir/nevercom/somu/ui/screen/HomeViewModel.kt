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

class HomeViewModel(private val api: Api) : ViewModel() {
    private val _state: MutableLiveData<HomeViewState> =
        MutableLiveData(HomeViewState(ViewState.Loading()))
    val state: LiveData<HomeViewState> = _state

    init {
        search()
    }

    private fun search() {
        _state.value = HomeViewState(ViewState.Loading())
        viewModelScope.launch {
            when (val response = api.search("god")) {
                is NetworkResponse.Success -> {
                    _state.value = HomeViewState(ViewState.Loaded(response.body.results))
                }
                is NetworkResponse.ServerError -> {
                    _state.value = HomeViewState(
                        ViewState.Error(
                            Throwable(
                                response.body?.errorMessage ?: "Something went wrong"
                            )
                        )
                    )
                }
                else -> {
                    _state.value = HomeViewState(
                        ViewState.Error(
                            Throwable("Something went wrong")
                        )
                    )
                }
            }
        }
    }
}

data class HomeViewState(
    val movies: ViewState<List<Movie>>
)