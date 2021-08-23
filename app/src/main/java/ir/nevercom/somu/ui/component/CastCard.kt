package ir.nevercom.somu.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun CastCard(
    profileUrl: String?,
    name: String,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier.width(72.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.1f))
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = name.initials()
            )
            Image(
                painter = rememberImagePainter(
                    data = profileUrl,
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp)
        )
    }
}

@Composable
fun CastPlaceHolder() {
    Column(
        modifier = Modifier.width(72.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .placeholder(
                    visible = true,
                    color = Color.LightGray.copy(alpha = 0.1f),
                    highlight = PlaceholderHighlight.fade(),
                )

        )
        Text(
            text = "name",
            modifier = Modifier
                .placeholder(
                    visible = true,
                    color = Color.LightGray.copy(alpha = 0.1f),
                    highlight = PlaceholderHighlight.fade(),
                )
        )
    }
}

fun String.initials() =
    this.split(Regex("(\\s)+"), limit = 2)
        .joinToString(separator = "") { it.first().uppercase() }