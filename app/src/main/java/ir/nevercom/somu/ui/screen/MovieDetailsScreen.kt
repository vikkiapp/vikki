package ir.nevercom.somu.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import ir.nevercom.somu.R
import ir.nevercom.somu.ViewState
import ir.nevercom.somu.model.Movie
import ir.nevercom.somu.model.sampleMovie
import ir.nevercom.somu.ui.theme.SomuTheme
import ir.nevercom.somu.ui.theme.bgColorEdge
import org.koin.androidx.compose.getViewModel

@Composable
fun MovieDetailsScreen(movie: Movie, viewModel: MovieDetailsViewModel = getViewModel()) {
    val state: MovieDetailsViewState by viewModel.state.observeAsState(
        MovieDetailsViewState(
            ViewState.Loading()
        )
    )
    var currentMovie = movie.copy()

    if (state.movie is ViewState.Loaded && state.movie.data != null) {
        //currentMovie = state.movie.data!!
    }

    Content(currentMovie)
}

@Composable
private fun Content(movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        Image(
            painter = rememberImagePainter(
                data = movie.posterPath,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.poster_1_blur) // TODO: Remove or change in production
                    transformations(BlurTransformation(LocalContext.current, 20f, 5f))
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .statusBarsHeight()
//                .background(Color.Gray.copy(alpha = 0.1f))
//        )
        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxSize()
                .statusBarsPadding()
            /*               .background(
                               Brush.linearGradient(
                                   0.2f to Color.Transparent,
                                   0.5f to bgColorEdge,
                                   1f to bgColorEdge,
                               )
                               *//*
                    Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            0.3f to bgColorEdge.copy(alpha = 0.7f),
                            0.5f to bgColorEdge.copy(alpha = 0.9f),
                            0.7f to bgColorEdge
                        )
                     *//*
                )*/
        ) {
            movie.tagline?.let {
                Text(
                    text = movie.tagline.uppercase(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.4f)
                )
            }
            PosterSection(
                movie = movie,
                modifier = Modifier.paddingFromBaseline(top = 42.dp)
            )
            movie.credits?.let {
                Spacer(modifier = Modifier.height(16.dp))
                CastsSection(movie.credits.cast)
            }
            movie.overview?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Storyline",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun CastsSection(casts: List<Cast>) {
    Text(
        text = "The Cast",
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    CastsList(casts)
}

@Composable
private fun CastsList(casts: List<Cast>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = casts.filter { it.profilePath != null }.take(10)
        ) { cast ->
            CastCard(cast, {})
        }
    }
}

@Composable
private fun CastCard(
    cast: Cast,
    onClick: (cast: Cast) -> Unit
) {

    Column(
        modifier = Modifier.width(64.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(
                data = cast.profilePath,

                ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .clickable {
                    onClick(cast)
                }
        )
        Text(
            text = cast.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
private fun PosterSection(modifier: Modifier = Modifier, movie: Movie) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        //.clip(RoundedCornerShape(8.dp)).background(Color.Gray.copy(alpha = 0.1f))
        ,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = movie.posterPath,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.poster_1) // TODO: Remove or change in production
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .border(2.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .width(140.dp)
                .aspectRatio(0.69f)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h6
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                movie.certification?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = "${movie.releaseDate.substring(0, 4)} - ${movie.genres?.first()?.name}"
                )
            }
            movie.genres?.let { genres ->

                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    genres.take(3).forEach {
                        Text(
                            text = it.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = darkRed, shape = RoundedCornerShape(16.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                .height(16.dp),
                            style = MaterialTheme.typography.caption.copy(
                                fontSize = 10.sp
                            )
                        )
                    }
                }

            }
            RatingBar(
                rating = (movie.voteAverage / 2).toFloat(),
                modifier = Modifier.height(20.dp),
                color = lightOrange
            )
            Text(text = "Runtime: ${movie.runtime} minutes")
            movie.credits?.let { credits ->
                Text(text = "Directed by ${credits.crew.find { it.job.lowercase() == "director" }?.name}")
            }
        }
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    SomuTheme {
        Surface {
            Content(movie = sampleMovie)
        }
    }
}