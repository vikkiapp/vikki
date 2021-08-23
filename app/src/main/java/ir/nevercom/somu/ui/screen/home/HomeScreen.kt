package ir.nevercom.somu.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.vkay.api.tmdb.models.TmdbImage
import ir.nevercom.somu.ui.component.MovieCard
import ir.nevercom.somu.ui.component.MovieCardPlaceHolder
import ir.nevercom.somu.util.ViewState

@Composable
fun HomeContent(
    vm: HomeViewModel = hiltViewModel(),
    onMovieClicked: (movieId: Int) -> Unit
) {
    val state = vm.state.observeAsState(HomeViewState.Empty)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Movies to Watch",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            when (val movies = state.value.movies) {
                is ViewState.Loaded -> {
                    items(items = movies.data!!, key = { it.id }) {
                        MovieCard(
                            url = it.poster?.get(TmdbImage.Quality.POSTER_W_185),
                            rating = (it.voteAverage / 2).toFloat(),
                            onClick = { onMovieClicked(it.id) }
                        )
                    }
                }
                else -> items(4) { MovieCardPlaceHolder() }

            }

        }
    }
}