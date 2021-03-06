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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.*
import ir.nevercom.somu.ui.component.CastCard
import ir.nevercom.somu.ui.component.MovieCard
import ir.nevercom.somu.ui.theme.bgColorEdge
import ir.nevercom.somu.util.ViewState
import ir.nevercom.somu.util.toReadableString
import kotlinx.coroutines.launch

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    vm: SearchScreenViewModel = hiltViewModel(),
    onMovieClicked: (id: Int) -> Unit,
    onShowClicked: (id: Int) -> Unit,
    onPersonClicked: (id: Int) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var query by rememberSaveable { mutableStateOf("") }
    val state = vm.state.observeAsState(SearchViewState.Empty)

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = { Text("Search for movies, shows, people...") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.isNotEmpty()) {
                        vm.search(query)
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
            when (state.value.result) {
                is ViewState.Loaded -> MovieGrid(
                    currentState = state.value,
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
@OptIn(com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
private fun MovieGrid(
    currentState: SearchViewState,
    onMovieClicked: (id: Int) -> Unit,
    onShowClicked: (id: Int) -> Unit,
    onPersonClicked: (id: Int) -> Unit,
) {

    val map by remember {
        mutableStateOf(
            currentState.result.data?.results?.groupBy { it.mediaType }?.toSortedMap()
        )
    }

    val pagerState = rememberPagerState()
    if (map!!.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            val coroutineScope = rememberCoroutineScope()

            Spacer(modifier = Modifier.height(16.dp))
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = MaterialTheme.colors.background.copy(alpha = 0.5f),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                map!!.entries.forEachIndexed { index, entry ->
                    Tab(
                        text = { Text("${entry.key.toReadableString()} (${entry.value.size})") },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top,
                count = map?.size!!
            ) { page ->
                TabContent(map!!, page, onMovieClicked, onShowClicked, onPersonClicked)
            }
        }
    }


}

@Composable
private fun TabContent(
    list: Map<MediaType, List<MediaTypeItem>>,
    page: Int,
    onMovieClicked: (id: Int) -> Unit,
    onShowClicked: (id: Int) -> Unit,
    onPersonClicked: (id: Int) -> Unit
) {
    when (val key = list.keys.toTypedArray()[page]) {
        MediaType.MOVIE -> {
            Grid(items = list[key] as List<TmdbMovie.Slim>) {
                MovieCard(
                    url = it.poster?.get(TmdbImage.Quality.POSTER_W_185),
                    title = it.title,
                    onClick = { onMovieClicked(it.id) },
                    rating = (it.voteAverage / 2).toFloat()
                )
            }
        }
        MediaType.TV -> {
            Grid(items = list[key] as List<TmdbShow.Slim>) {
                MovieCard(
                    url = it.poster?.get(TmdbImage.Quality.POSTER_W_185),
                    title = it.title,
                    onClick = { onShowClicked(it.id) },
                    rating = (it.voteAverage / 2).toFloat()
                )
            }
        }
        MediaType.PERSON -> {
            Grid(items = list[key] as List<TmdbPerson.Slim>) {
                CastCard(
                    profileUrl = it.profile?.get(TmdbImage.Quality.PROFILE_W_154),
                    onClick = { onPersonClicked(it.id) },
                    name = it.name
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> Grid(
    items: List<T>,
    content: @Composable (item: T) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4),
        modifier = Modifier.padding(horizontal = 12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items = items) {
            Box(modifier = Modifier.padding(4.dp)) {
                content(it)
            }
        }
    }
}