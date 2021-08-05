package ir.nevercom.somu.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import de.vkay.api.tmdb.models.TmdbImage
import ir.nevercom.somu.ui.component.MovieCard
import ir.nevercom.somu.ui.theme.bgColorEdge
import ir.nevercom.somu.util.ViewState
import org.koin.androidx.compose.getViewModel

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    vm: SearchScreenViewModel = getViewModel(),
    onMovieClicked: (movieId: Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val query = remember { mutableStateOf("") }
    val state = vm.state.observeAsState()
    val currentState = state.value!!

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query.value,
            onValueChange = { query.value = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = { Text("Search for movies...") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value.isNotEmpty()) {
                        vm.search(query.value)
                        keyboardController?.hide()
                    }
                }
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentState.movies) {
                is ViewState.Loaded -> MovieGrid(currentState, onMovieClicked)
                is ViewState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Search for a movie")
                    }
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(
                        Brush.verticalGradient(
                            0.3f to bgColorEdge.copy(alpha = 0.7f),
                            0.6f to Color.Transparent,
                        )
                    )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieGrid(
    currentState: SearchViewState,
    onMovieClicked: (movieId: Int) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4),
        modifier = Modifier.padding(horizontal = 12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items = currentState.movies.data?.results!!) {
            Box(modifier = Modifier.padding(4.dp)) {
                MovieCard(
                    url = it.poster?.get(TmdbImage.Quality.POSTER_W_185),
                    onClick = { onMovieClicked(it.id) },
                    rating = (it.voteAverage / 2).toFloat()
                )
            }
        }
    }
}