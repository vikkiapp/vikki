package ir.nevercom.somu.ui.screen.discover

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import de.vkay.api.tmdb.models.TmdbImage
import de.vkay.api.tmdb.models.TmdbMovie
import ir.nevercom.somu.ui.component.DetailedMovieCard
import ir.nevercom.somu.ui.component.MovieCard
import ir.nevercom.somu.util.items

@Composable
fun DiscoverScreen(
    vm: DiscoverScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onShowClicked: (showId: Int) -> Unit
) {
    val pagingItems = vm.movies.collectAsLazyPagingItems()
    if (pagingItems.loadState.refresh == LoadState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        DiscoverScreen(
            items = pagingItems,
            title = vm.title,
            onBackClicked = onBackClicked,
            onMovieClicked = onMovieClicked,
            onShowClicked = onShowClicked,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DiscoverScreen(
    items: LazyPagingItems<TmdbMovie.Slim>,
    title: String,
    onBackClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onShowClicked: (showId: Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            var isGridView by remember { mutableStateOf(false) }
            TopBar(
                title = title,
                isGridView = isGridView,
                onBackClicked = onBackClicked,
                onDisplayOptionClicked = { isGridView = !isGridView }
            )
            Crossfade(targetState = isGridView) { gridView ->
                if (gridView) {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(4),
                        modifier = Modifier.padding(horizontal = 12.dp),
                    ) {
                        items(items) {
                            Box(modifier = Modifier.padding(4.dp)) {
                                MovieCard(
                                    url = it?.poster?.get(TmdbImage.Quality.POSTER_W_185),
                                    title = it?.title!!,
                                    onClick = { onMovieClicked(it.id) },
                                    rating = (it.voteAverage / 2).toFloat()
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(items) {
                            DetailedMovieCard(
                                url = it?.poster?.get(TmdbImage.Quality.POSTER_W_185),
                                title = it?.title!!,
                                overview = it.overview,
                                rating = it.voteAverage,
                                releaseDate = it.releaseDate?.date?.year,
                                originalLanguage = it.originalLanguage,
                                onClick = { onMovieClicked(it.id) }
                            )
                        }
                    }
                }

            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsHeight()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colors.background.copy(alpha = 0.7f))
        )
    }
}

@Composable
private fun TopBar(
    title: String,
    isGridView: Boolean = false,
    onBackClicked: () -> Unit,
    onDisplayOptionClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(end = 16.dp)
    ) {
        IconButton(
            onClick = { onBackClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Button"
            )
        }
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            onClick = { onDisplayOptionClicked() }
        ) {
            Crossfade(targetState = isGridView) { gridView ->
                if (gridView) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Switch to List View"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.GridOn,
                        contentDescription = "Switch to Grid View"
                    )
                }
            }

        }
    }
}