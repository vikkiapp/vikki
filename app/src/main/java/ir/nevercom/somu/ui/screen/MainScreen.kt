package ir.nevercom.somu.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.BottomNavigation
import com.google.accompanist.insets.ui.TopAppBar
import ir.nevercom.somu.R
import ir.nevercom.somu.ui.NavScreen
import ir.nevercom.somu.ui.screen.home.HomeContent
import ir.nevercom.somu.ui.screen.search.SearchScreen
import ir.nevercom.somu.ui.theme.SomuTheme
import ir.nevercom.somu.ui.theme.bgColorEdge

@Composable
fun MainScreen(onMovieClicked: (movieId: Int) -> Unit) {
    val navController = rememberNavController()
    val navScreens = listOf(
        NavScreen.Home,
        NavScreen.Search,
        NavScreen.Friends,
        NavScreen.Profile
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                backgroundColor = Color.Transparent,
                contentColor = Color.White,
                elevation = 0.dp,
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.statusBars,
                    applyStart = true,
                    applyTop = true,
                    applyEnd = true,
                )
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = bgColorEdge,
                contentColor = Color.White,
                contentPadding = rememberInsetsPaddingValues(
                    LocalWindowInsets.current.navigationBars
                )
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                navScreens.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                return@BottomNavigationItem
                            }
                            navController.navigate(screen.route) {
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        }
                    )
                }
            }

        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = NavScreen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(NavScreen.Home.route) {
                HomeContent(onMovieClicked = onMovieClicked)
            }
            composable(NavScreen.Search.route) {
                SearchScreen(onMovieClicked = onMovieClicked)
            }
            composable(NavScreen.Friends.route) { Text("Friends") }
            composable(NavScreen.Profile.route) { Text("Profile") }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    SomuTheme {
        MainScreen({})
    }
}