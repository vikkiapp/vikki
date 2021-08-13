package ir.nevercom.somu.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : NavScreen("home", "Home", Icons.Default.Home)
    object Search : NavScreen("search", "Search", Icons.Default.Search)
    object Friends : NavScreen("friends", "Friends Feed", Icons.Default.Group)
    object Profile : NavScreen("profile", "Profile", Icons.Default.Person)
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object MovieDetails : Screen("details/movie/{id}") {
        fun createRoute(id: Int): String = "details/movie/$id"
    }

    object PersonDetails : Screen("person/{id}") {
        fun createRoute(id: Int): String = "person/$id"
    }
}