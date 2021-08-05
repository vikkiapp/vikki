package ir.nevercom.somu.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import ir.nevercom.somu.BuildConfig
import ir.nevercom.somu.R
import ir.nevercom.somu.ui.theme.SomuTheme

@Composable
fun MovieCard(url: String?, rating: Float = 0.0f, onClick: () -> Unit) {
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
                    //placeholder(previewPlaceholder)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color.Black.copy(alpha = 0.4f))
                .align(Alignment.BottomCenter)
        )
        RatingBar(
            rating = rating,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .height(8.dp)
                .align(Alignment.BottomCenter)
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
                    MovieCard("", 4f) {}
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
            MovieCard("", 3.5f) {}
        }
    }
}