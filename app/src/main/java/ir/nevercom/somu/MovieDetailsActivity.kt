package ir.nevercom.somu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.nevercom.somu.ui.screen.movieDetails.MovieDetailsScreen
import ir.nevercom.somu.ui.screen.movieDetails.MovieDetailsViewModel
import ir.nevercom.somu.ui.theme.SomuTheme
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MovieDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieId = intent.getIntExtra("movieId", 0)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val viewModel: MovieDetailsViewModel by inject { parametersOf(movieId) }
        setContent {

            SomuTheme {
                // Remember a SystemUiController
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                SideEffect {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = false,
                        isNavigationBarContrastEnforced = false
                    )

                    // setStatusBarsColor() and setNavigationBarsColor() also exist
                }

                ProvideWindowInsets {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        MovieDetailsScreen(viewModel = viewModel, onBackClicked = {
                            finish()
                        })
                    }
                }
            }
        }
    }
}
