package ir.nevercom.somu.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.BottomNavigation
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ir.nevercom.somu.R
import ir.nevercom.somu.repositories.UserRepository
import ir.nevercom.somu.ui.NavScreen
import ir.nevercom.somu.ui.Screen
import ir.nevercom.somu.ui.navScreens
import ir.nevercom.somu.ui.screen.home.HomeContent
import ir.nevercom.somu.ui.screen.login.LoginScreen
import ir.nevercom.somu.ui.screen.movieDetails.MovieDetailsScreen
import ir.nevercom.somu.ui.screen.person.PersonScreen
import ir.nevercom.somu.ui.screen.search.SearchScreen
import ir.nevercom.somu.ui.screen.showDetails.ShowDetailsScreen
import ir.nevercom.somu.ui.theme.bgColorEdge

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(userRepository: UserRepository) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            if (currentDestination?.route in navScreens.associateBy { it.route }) {
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
            }
        },
        bottomBar = {
            if (currentDestination?.route in navScreens.associateBy { it.route }) {
                BottomNavigation(
                    backgroundColor = bgColorEdge,
                    contentColor = Color.White,
                    contentPadding = rememberInsetsPaddingValues(
                        LocalWindowInsets.current.navigationBars
                    )
                ) {
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
        }
    ) { innerPadding ->
        val startDestination = if (userRepository.isLoggedIn()) {
            NavScreen.Home.route
        } else {
            Screen.Login.route
        }
        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { initial, _ ->
                if (initial.isBottomNavScreen()) {
                    slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(300)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    )
                }

            },
            exitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = { _, target ->
                if (target.isBottomNavScreen()) {
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(300)
                    )
                } else {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    )

                }
            },
        ) {
            addBottomNavScreens(navController)

            composable(
                route = Screen.Login.route,
                enterTransition = { _, _ -> fadeIn() },
                exitTransition = { _, _ -> fadeOut() },
                popEnterTransition = { _, _ -> fadeIn() },
                popExitTransition = { _, _ -> fadeOut() },
            ) {
                LoginScreen(
                    onLoggedIn = {
                        navController.navigate(NavScreen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(
                route = Screen.MovieDetails.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                MovieDetailsScreen(
                    onBackClicked = { navController.popBackStack() },
                    onMovieClicked = { id ->
                        navController.navigate(Screen.MovieDetails.createRoute(id))
                    },
                    onPersonClicked = { id ->
                        navController.navigate(Screen.PersonDetails.createRoute(id))
                    }
                )
            }
            composable(
                route = Screen.ShowDetails.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                ShowDetailsScreen(
                    onBackClicked = { navController.popBackStack() },
                    onPersonClicked = { id ->
                        navController.navigate(Screen.PersonDetails.createRoute(id))
                    }
                )
            }
            composable(
                route = Screen.PersonDetails.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                PersonScreen(
                    onBackClicked = { navController.popBackStack() },
                    onMovieClicked = { id ->
                        navController.navigate(Screen.MovieDetails.createRoute(id))
                    },
                    onShowClicked = { id ->
                        navController.navigate(Screen.ShowDetails.createRoute(id))
                    },
                )
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addBottomNavScreens(navController: NavHostController) {
    composable(
        route = NavScreen.Home.route,
        enterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        exitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        },
        popEnterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        popExitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        }
    ) {
        HomeContent(
            onMovieClicked = { id ->
                navController.navigate(Screen.MovieDetails.createRoute(id))
            }
        )
    }
    composable(
        route = NavScreen.Search.route,
        enterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        exitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        },
        popEnterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        popExitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        }
    ) {
        SearchScreen(
            onMovieClicked = { id ->
                navController.navigate(Screen.MovieDetails.createRoute(id))
            },
            onShowClicked = { id ->
                navController.navigate(Screen.ShowDetails.createRoute(id))
            },
            onPersonClicked = { id ->
                navController.navigate(Screen.PersonDetails.createRoute(id))
            }
        )
    }
    composable(
        route = NavScreen.Friends.route,
        enterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        exitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        },
        popEnterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        popExitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        }
    ) {
        Text("Friends")
    }
    composable(
        route = NavScreen.Profile.route,
        enterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        exitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        },
        popEnterTransition = { initial, _ ->
            if (!initial.isBottomNavScreen()) {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(300)
                )
            } else {
                fadeIn()
            }
        },
        popExitTransition = { _, target ->
            if (!target.isBottomNavScreen()) {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                )
            } else {
                fadeOut()
            }
        }
    ) {
        Text("Profile")
    }
}

private fun NavBackStackEntry.isBottomNavScreen() =
    this.destination.hierarchy.any { it.route in navScreens.map { screen -> screen.route } }