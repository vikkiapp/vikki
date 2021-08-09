package ir.nevercom.somu.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Facebook
import compose.icons.fontawesomeicons.brands.Imdb
import compose.icons.fontawesomeicons.brands.Instagram
import compose.icons.fontawesomeicons.brands.Twitter

@Composable
fun SocialMediaItem(handle: String, type: SocialMedia) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = type.icon,
            contentDescription = type.contentDescription,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = handle,
            style = MaterialTheme.typography.caption
        )
    }
}

enum class SocialMedia(val icon: ImageVector, val contentDescription: String) {
    FACEBOOK(FontAwesomeIcons.Brands.Facebook, "Facebook Account"),
    TWITTER(FontAwesomeIcons.Brands.Twitter, "Twitter Account"),
    INSTAGRAM(FontAwesomeIcons.Brands.Instagram, "Instagram Account"),
    IMDB(FontAwesomeIcons.Brands.Imdb, "Imdb Account"),
}
