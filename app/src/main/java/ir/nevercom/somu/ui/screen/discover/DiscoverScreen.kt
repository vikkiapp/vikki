package ir.nevercom.somu.ui.screen.discover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import de.vkay.api.tmdb.models.TmdbImage
import de.vkay.api.tmdb.models.TmdbMovie
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
            TopBar(title = title, onBackClicked = onBackClicked)
            LazyVerticalGrid(
                cells = GridCells.Fixed(4),
                modifier = Modifier.padding(horizontal = 12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
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
private fun TopBar(title: String, onBackClicked: () -> Unit) {
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
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}