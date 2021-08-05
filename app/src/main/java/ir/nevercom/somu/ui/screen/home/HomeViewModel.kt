package ir.nevercom.somu.ui.screen.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbMovie
import ir.nevercom.somu.repositories.Api
import ir.nevercom.somu.util.ViewState
import kotlinx.coroutines.launch

class HomeViewModel(private val api: Api, private val tmdb: TMDb) : ViewModel() {
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

    fun getList() {
        Log.d("TAG", "getList: ")
        viewModelScope.launch {
            when (val response = tmdb.searchService.movie(query = "god")) {
                is NetworkResponse.Success -> {
                    _state.value = HomeViewState(ViewState.Loaded(response.body.results))
                }
            }
        }

    }
}

data class HomeViewState(
    val movies: ViewState<List<TmdbMovie.Slim>>
)