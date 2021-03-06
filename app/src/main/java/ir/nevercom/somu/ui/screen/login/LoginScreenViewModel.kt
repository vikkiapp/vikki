package ir.nevercom.somu.ui.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.nevercom.somu.model.body.Login
import ir.nevercom.somu.repositories.Api
import ir.nevercom.somu.repositories.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val api: Api,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState: MutableLiveData<LoginViewState> = MutableLiveData(LoginViewState())
    val viewState: LiveData<LoginViewState> = _viewState

    private fun currentState(): LoginViewState = _viewState.value!!

    fun login(email: String, password: String) = viewModelScope.launch {
        _viewState.value = currentState().copy(isLoading = true, isLoggedIn = false)
        when (val response = api.login(Login(email, password))) {

            is NetworkResponse.Success -> {

                userRepository.saveToken(response.body.token)
                userRepository.saveUser(response.body.user)

                _viewState.value = currentState().copy(
                    isLoading = false,
                    isLoggedIn = true,
                    errorMessage = null
                )
            }
            is NetworkResponse.ServerError -> _viewState.value = currentState().copy(
                isLoading = false,
                isLoggedIn = false,
                errorMessage = response.body?.errorMessage ?: "Something went wrong"
            )
            else -> _viewState.value = currentState().copy(
                isLoading = false,
                isLoggedIn = false,
                errorMessage = "Unknown error"
            )
        }
    }
}

data class LoginViewState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)