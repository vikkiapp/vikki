package ir.nevercom.somu.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.vkay.api.tmdb.models.TmdbImage
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbShow
import ir.nevercom.somu.ui.component.CastCard
import ir.nevercom.somu.ui.component.MovieCard
import ir.nevercom.somu.ui.theme.bgColorEdge
import ir.nevercom.somu.util.ViewState

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    vm: SearchScreenViewModel = hiltViewModel(),
    onMovieClicked: (id: Int) -> Unit,
    onShowClicked: (id: Int) -> Unit,
    onPersonClicked: (id: Int) -> Unit,
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
            when (currentState.result) {
                is ViewState.Loaded -> MovieGrid(
                    currentState = currentState,
                    onMovieClicked = onMovieClicked,
                    onShowClicked = onShowClicked,
                    onPersonClicked = onPersonClicked
                )
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

//TODO: Search Screen UI/UX needs serious Attention, Code is messy and should be cleaned up
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieGrid(
    currentState: SearchViewState,
    onMovieClicked: (id: Int) -> Unit,
    onShowClicked: (id: Int) -> Unit,
    onPersonClicked: (id: Int) -> Unit,
) {
    val movies = mutableListOf<TmdbMovie.Slim>()
    val shows = mutableListOf<TmdbShow.Slim>()
    val persons = mutableListOf<TmdbPerson.Slim>()
    currentState.result.data?.results?.forEach {
        when (it) {
            is TmdbMovie.Slim -> movies.add(it)
            is TmdbShow.Slim -> shows.add(it)
            is TmdbPerson.Slim -> persons.add(it)
        }
    }
    var state by remember { mutableStateOf(0) }
    if (movies.size > 0 || shows.size > 0 || persons.size > 0) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(16.dp))
            TabRow(selectedTabIndex = state, modifier = Modifier.padding(horizontal = 16.dp)) {
                if (movies.size > 0) {
                    Tab(
                        text = { Text("Movies (${movies.size})") },
                        selected = state == 0,
                        onClick = { state = 0 }
                    )
                }
                if (shows.size > 0) {
                    Tab(
                        text = { Text("Shows (${shows.size})") },
                        selected = state == 1,
                        onClick = { state = 1 }
                    )
                }
                if (persons.size > 0) {
                    Tab(
                        text = { Text("People (${persons.size})") },
                        selected = state == 2,
                        onClick = { state = 2 }
                    )
                }
            }
            when (state) {
                0 -> {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(4),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(items = movies) {
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
                1 -> {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(4),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(items = shows) {
                            Box(modifier = Modifier.padding(4.dp)) {
                                MovieCard(
                                    url = it.poster?.get(TmdbImage.Quality.POSTER_W_185),
                                    onClick = { onShowClicked(it.id) },
                                    rating = (it.voteAverage / 2).toFloat()
                                )
                            }
                        }
                    }
                }
                2 -> {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(4),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(items = persons) {
                            Box(modifier = Modifier.padding(4.dp)) {
                                CastCard(
                                    profileUrl = it.profile?.get(TmdbImage.Quality.PROFILE_W_154),
                                    onClick = { onPersonClicked(it.id) },
                                    name = it.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}