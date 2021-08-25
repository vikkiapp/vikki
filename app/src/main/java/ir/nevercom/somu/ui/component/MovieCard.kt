package ir.nevercom.somu.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import ir.nevercom.somu.R
import ir.nevercom.somu.ui.theme.SomuTheme

@Composable
fun MovieCard(url: String?, title: String = "", rating: Float = 0.0f, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(96.dp)
            .aspectRatio(0.69f)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Gray.copy(alpha = 0.1f))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
        Image(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    error(R.drawable.no_image)
                    fallback(R.drawable.no_image)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
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

@Composable
fun ExtendedMovieCard(
    url: String?, rating: Float, tag: String, details: String, onClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.width(96.dp)) {
        Box {
            MovieCard(
                url = url,
                rating = rating,
                onClick = onClick
            )
            Text(
                text = tag,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
        Text(
            text = details,
            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
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
                    MovieCard(
                        url = "",
                        rating = 4f
                    ) {}
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
            MovieCard(url = "", rating = 3.5f) {}
        }
    }
}

@Preview(name = "Extended")
@Composable
fun ExtendedMoviePosterPreview() {
    SomuTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.padding(16.dp)
        ) {
            ExtendedMovieCard("", 4f, "Movie", "John Doe") {}
        }
    }
}