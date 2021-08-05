package ir.nevercom.somu.util

sealed class ViewState<T>(val data: T? = null) {
    class Loading<T>(data: T? = null) : ViewState<T>(data)
    class Error<T>(val throwable: Throwable, data: T? = null) : ViewState<T>(data)
    class Loaded<T>(data: T) : ViewState<T>(data)
    class Empty<T> : ViewState<T>()

    fun isLoading(): Boolean = this is Loading
}