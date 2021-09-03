package ir.nevercom.somu.ui.screen.discover

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPage
import de.vkay.api.tmdb.models.TmdbShow
import ir.nevercom.somu.util.ViewState

@Composable
fun DiscoverScreen(
    vm: DiscoverScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onShowClicked: (showId: Int) -> Unit
) {
    val state = vm.state.observeAsState(DiscoverScreenViewState.Empty)

    Crossfade(targetState = state.value.movies) { currentState ->
        when (currentState) {
            is ViewState.Loaded -> {
                DiscoverScreen(
                    movies = currentState.data!!,
                    shows = state.value.shows,
                    onBackClicked = onBackClicked,
                    onMovieClicked = onMovieClicked,
                    onShowClicked = onShowClicked,
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
internal fun DiscoverScreen(
    movies: TmdbPage<TmdbMovie.Slim>,
    shows: ViewState<TmdbPage<TmdbShow.Slim>>,
    onBackClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onShowClicked: (showId: Int) -> Unit,
) {

}