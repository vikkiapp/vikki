package ir.nevercom.somu.ui.screen.movieDetails

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.google.accompanist.insets.statusBarsPadding
import de.vkay.api.tmdb.models.TmdbImage
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbReleaseDate
import ir.nevercom.somu.R
import ir.nevercom.somu.ui.component.CastCard
import ir.nevercom.somu.ui.component.ExpandingText
import ir.nevercom.somu.ui.component.MovieCard
import ir.nevercom.somu.ui.component.RatingBar
import ir.nevercom.somu.ui.theme.darkRed
import ir.nevercom.somu.ui.theme.lightOrange
import ir.nevercom.somu.util.ViewState
import ir.nevercom.somu.util.ifDirectorFound

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onMovieClicked: (id: Int) -> Unit,
    onPersonClicked: (id: Int) -> Unit
) {
    val state = viewModel.state.observeAsState(MovieDetailsViewState.Empty)


    Crossfade(targetState = state.value.movie) { movieState ->
        when (movieState) {
            is ViewState.Loaded -> {
                MovieDetailsScreen(
                    movie = movieState.data!!,
                    cast = state.value.cast,
                    crew = state.value.crew,
                    releaseDates = state.value.releaseDates,
                    recommendations = state.value.similar,
                    onBackClicked = onBackClicked,
                    onMovieClicked = onMovieClicked,
                    onPersonClicked = onPersonClicked
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }


}

@Composable
internal fun MovieDetailsScreen(
    movie: TmdbMovie,
    cast: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>>,
    crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    releaseDates: ViewState<Map<String, List<TmdbReleaseDate>>>,
    recommendations: ViewState<List<TmdbMovie.Slim>>,
    onBackClicked: () -> Unit,
    onMovieClicked: (Int) -> Unit,
    onPersonClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        Image(
            painter = rememberImagePainter(
                data = movie.poster?.get(TmdbImage.Quality.POSTER_W_185),
                builder = {
                    crossfade(true)
                    //placeholder(R.drawable.poster_1_blur) // TODO: Remove or change in production
                    transformations(BlurTransformation(LocalContext.current, 15f, 3f))
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(movie = movie, onBackClicked = onBackClicked)
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                PosterSection(
                    movie = movie,
                    crew = crew,
                    releaseDates = releaseDates,
                    modifier = Modifier.padding(top = 8.dp),
                    onPersonClicked = onPersonClicked
                )
                if (cast is ViewState.Loaded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CastsSection(
                        casts = cast.data!!,
                        onClick = { onPersonClicked(it.id) }
                    )
                }
                movie.overview.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Storyline",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ExpandingText(
                        text = movie.overview,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )
                }
                if (recommendations is ViewState.Loaded) {
                    RecommendationsSection(
                        filmList = recommendations.data!!,
                        onMovieClicked = onMovieClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendationsSection(
    filmList: List<TmdbMovie.Slim>,
    onMovieClicked: (Int) -> Unit,
) {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Recommendations",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(start = 16.dp)
        )
        Divider(color = Color.White.copy(alpha = 0.75f), thickness = 1.dp)
    }
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items = filmList) { item ->
            MovieCard(
                url = item.poster?.get(TmdbImage.Quality.POSTER_W_185),
                rating = (item.voteAverage / 2).toFloat(),
                title = item.title,
                onClick = { onMovieClicked(item.id) }
            )
        }
    }
}

@Composable
private fun TopBar(movie: TmdbMovie, onBackClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(end = 16.dp)
    ) {
        IconButton(
            onClick = { onBackClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Button"
            )
        }

        Text(
            text = movie.title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


    }
}

@Composable
private fun CastsSection(
    casts: List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>,
    onClick: (cast: TmdbPerson.Slim) -> Unit
) {
    Text(
        text = "The Cast",
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    CastsList(casts, onClick)
}

@Composable
private fun CastsList(
    casts: List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>,
    onClick: (cast: TmdbPerson.Slim) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = casts.filter { it.first.profile != null }.take(10)
        ) { cast ->
            CastCard(
                profileUrl = cast.first.profile?.get(TmdbImage.Quality.PROFILE_W_154),
                name = cast.first.name,
                onClick = { onClick(cast.first) }
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
private fun PosterSection(
    modifier: Modifier = Modifier,
    movie: TmdbMovie,
    crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    releaseDates: ViewState<Map<String, List<TmdbReleaseDate>>>,
    onPersonClicked: (Int) -> Unit
) {
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
                data = movie.poster?.get(TmdbImage.Quality.POSTER_W_500),
                builder = {
                    crossfade(true)
                    error(R.drawable.no_image)
                    fallback(R.drawable.no_image)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .border(2.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .width(140.dp)
                .aspectRatio(0.69f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.1f))
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                var certification = movie.release_dates?.results?.find { it.iso_3166_1 == "US" }?.release_dates?.find { it.certification?.isNotEmpty()!! }?.certification
                val certification = when (releaseDates) {
                    is ViewState.Loaded -> {
                        val cert = releaseDates.data?.get("US")
                            ?.find { it.certification.isNotEmpty() }?.certification
                        cert ?: "N/A"
                    }
                    else -> "N/A"
                }

                Text(
                    text = certification,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )

                val date = movie.releaseDate?.date?.year
                val releaseDate = if (date == null || date < 1900) {
                    movie.status.name.lowercase().replaceFirstChar { it.uppercase() }
                } else {
                    date.toString()
                }
                Text(
                    text = releaseDate,
                    style = MaterialTheme.typography.caption,
                )
            }
            movie.genres.let { genres ->

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(
                    rating = (movie.voteAverage / 2).toFloat(),
                    modifier = Modifier.height(20.dp),
                    color = lightOrange
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${movie.voteAverage})",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption,
                )
            }
            Text(
                text = "Runtime: ${movie.runtime} minutes",
                style = MaterialTheme.typography.caption,
            )
            if (crew is ViewState.Loaded) {
                crew.data?.ifDirectorFound { director ->
                    DirectorText(director, onPersonClicked)
                }
            }
        }
    }
}

@Composable
private fun DirectorText(
    director: TmdbPerson.Slim,
    onPersonClicked: (Int) -> Unit
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.4.sp,
                color = Color.White
            )
        ) {
            append("Directed by: ")
        }
        pushStringAnnotation(
            tag = "director",
            annotation = director.id.toString()
        )
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                letterSpacing = 0.4.sp,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(director.name)
        }
        pop()
    }
    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "director",
                start = offset,
                end = offset
            ).firstOrNull()?.let { annotation ->
                onPersonClicked(annotation.item.toInt())
            }
        }
    )
}