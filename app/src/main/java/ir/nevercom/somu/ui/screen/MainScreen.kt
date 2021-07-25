package ir.nevercom.somu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import ir.nevercom.somu.BuildConfig
import ir.nevercom.somu.R
import ir.nevercom.somu.ViewState.Loaded
import ir.nevercom.somu.model.Movie
import ir.nevercom.somu.ui.Screen
import ir.nevercom.somu.ui.theme.SomuTheme
import ir.nevercom.somu.ui.theme.bgColorEdge
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(onMovieClicked: (movie: Movie) -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        Screen.Home,
        Screen.Friends,
        Screen.Profile
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Somu") },
                backgroundColor = Color.Transparent,
                contentColor = Color.White,
                elevation = 0.dp
            )
        },
        bottomBar = {
            // [Problem]: Have a Fullscreen Composable, inside Scaffold, just to make use of single
            // Navigation stack.
            //
            // [Workaround]: This workaround is just for testing purpose.
            // It seems that it's not possible to make two separate NavHost to interconnect,
            // or to share a single NavController.
            // This workaround hides BottomNavigation (and AppBar if needed) to mimic a fullscreen
            // Composable, and still place that Composable inside current NavHost, living inside
            // the Scaffold.
            // To test this just navigate to "full" route, when `onMovieClicked` lambda is triggered.
            if (currentDestination?.route in screens.associateBy { it.route }) {
                BottomNavigation(backgroundColor = bgColorEdge, contentColor = Color.White) {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentDestination = navBackStackEntry?.destination
                    screens.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeContent(onMovieClicked = onMovieClicked)
            }
            composable(Screen.Friends.route) { Text("Friends") }
            composable(Screen.Profile.route) { Text("Profile") }
            composable("full") {
                Text("full screen")
            }
        }
    }
}

@Composable
fun HomeContent(
    vm: HomeViewModel = getViewModel(),
    onMovieClicked: (movie: Movie) -> Unit
) {
    val state = vm.state.observeAsState()
    val currentState: HomeViewState = state.value!!

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
            when (val movies = currentState.movies) {
                is Loaded -> {
                    items(items = movies.data!!) {
                        MovieCard(
                            url = it.posterPath!!
                        ) {
                            onMovieClicked(it)
                        }
                    }
                }
                else -> items(4) { MovieCardPlaceHolder() }

            }

        }
    }
}

@Composable
fun MovieCard(url: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(96.dp)
            .aspectRatio(0.69f)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Gray.copy(alpha = 0.1f))
            .clickable(onClick = onClick)
    ) {
        val previewPlaceholder = if (BuildConfig.DEBUG) {
            listOf(
                R.drawable.poster_1,
                R.drawable.poster_2,
                R.drawable.poster_3,
                R.drawable.poster_4
            ).random()
        } else {
            0
        }
        Image(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    placeholder(previewPlaceholder)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MovieCardPlaceHolder() {
    Box(
        modifier = Modifier
            .width(96.dp)
            .aspectRatio(0.69f)
            .placeholder(
                visible = true,
                color = Color.LightGray.copy(alpha = 0.1f),
                highlight = PlaceholderHighlight.shimmer(),
            )
    )

}

@Preview(name = "List of Posters")
@Composable
fun MoviePosterListPreview() {
    SomuTheme {
        Surface(color = MaterialTheme.colors.background) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(6) {
                    MovieCard(url = "") {

                    }
                }

            }
        }
    }
}


@Preview(name = "Single Poster")
@Composable
fun MoviePosterPreview() {
    SomuTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.padding(16.dp)
        ) {
            MovieCard(url = "") {}
        }
    }
}

@Preview()
@Composable
fun PreviewMainScreen() {
    SomuTheme {
        MainScreen({})
    }
}