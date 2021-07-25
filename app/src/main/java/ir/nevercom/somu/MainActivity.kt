package ir.nevercom.somu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.nevercom.somu.model.ModelPreferencesManager
import ir.nevercom.somu.model.sampleMovie
import ir.nevercom.somu.repositories.UserRepository
import ir.nevercom.somu.ui.screen.LoginScreen
import ir.nevercom.somu.ui.screen.MainScreen
import ir.nevercom.somu.ui.screen.MovieDetailsScreen
import ir.nevercom.somu.ui.theme.SomuTheme
import ir.nevercom.somu.ui.theme.bgColorEdge
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ModelPreferencesManager.with(application)

        setContent {

            SomuTheme {
                // Remember a SystemUiController
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                SideEffect {
                    // Update all of the system bar colors to be a certain color, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = bgColorEdge,
                        darkIcons = false
                    )

                    // setStatusBarsColor() and setNavigationBarsColor() also exist
                }

                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppContent()
                }
            }
        }
    }

    @Composable
    private fun AppContent(userRepository: UserRepository = get()) {
        val navController = rememberNavController()

        val startDestination = if (userRepository.isLoggedIn()) "main" else "login"
        NavHost(navController = navController, startDestination = startDestination) {
            composable("main") {
                MainScreen(onMovieClicked = {
                    Log.d("Movie", it.toString())
                    startActivity(
                        Intent(
                            this@MainActivity,
                            MovieDetailsActivity::class.java
                        ).putExtra("movie", it)
                    )
                })
            }
            composable("login") {
                LoginScreen(
                    onLoggedIn = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            // Just for testing purpose.
            composable("movie.details") {
                MovieDetailsScreen(movie = sampleMovie)
            }
        }
    }
}
