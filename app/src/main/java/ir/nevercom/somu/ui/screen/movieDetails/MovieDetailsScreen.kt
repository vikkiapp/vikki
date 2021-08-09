package ir.nevercom.somu.ui.screen.movieDetails

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import ir.nevercom.somu.ui.component.RatingBar
import ir.nevercom.somu.ui.theme.darkRed
import ir.nevercom.somu.ui.theme.lightOrange
import ir.nevercom.somu.util.ViewState

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onPersonClicked: (Int) -> Unit
) {
    val state = viewModel.state.observeAsState()
    val currentState = state.value!!


    Crossfade(targetState = currentState.movie) { movieState ->
        when (movieState) {
            is ViewState.Loaded -> {
                Content(
                    movie = movieState.data!!,
                    cast = currentState.cast,
                    crew = currentState.crew,
                    releaseDates = currentState.releaseDates,
                    onBackClicked = onBackClicked,
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
private fun Content(
    movie: TmdbMovie,
    cast: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>>,
    crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    releaseDates: ViewState<Map<String, List<TmdbReleaseDate>>>,
    onBackClicked: () -> Unit,
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
                    modifier = Modifier.padding(top = 8.dp)
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
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )
                }
            }

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
        movie.tagline?.let {
            Text(
                text = it.uppercase(),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.4f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

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
            CastCard(cast, onClick)
        }
    }
}

@Composable
private fun CastCard(
    cast: Pair<TmdbPerson.Slim, TmdbPerson.CastRole>,
    onClick: (cast: TmdbPerson.Slim) -> Unit
) {

    Column(
        modifier = Modifier.width(72.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(
                data = cast.first.profile?.get(TmdbImage.Quality.PROFILE_W_154),
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.1f))
                .clickable {
                    onClick(cast.first)
                }
        )
        Text(
            text = cast.first.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp)
        )
    }
}

@SuppressLint("NewApi")
@Composable
private fun PosterSection(
    modifier: Modifier = Modifier,
    movie: TmdbMovie,
    crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    releaseDates: ViewState<Map<String, List<TmdbReleaseDate>>>,
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
                    //placeholder(R.drawable.poster_1) // TODO: Remove or change in production
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
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h6
            )
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
                Text(
                    text = "$date",
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
                Text(
                    text = "Directed by ${crew.data?.find { it.second.job.lowercase() == "director" }?.first?.name}",
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}