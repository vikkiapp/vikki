package ir.nevercom.somu.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Friends : Screen("friends", "Friends Feed", Icons.Default.Group)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

}