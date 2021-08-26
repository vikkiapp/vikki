package ir.nevercom.somu.ui.screen.showDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import de.vkay.api.tmdb.models.*
import ir.nevercom.somu.R
import ir.nevercom.somu.ui.component.CastCard
import ir.nevercom.somu.ui.component.CastPlaceHolder
import ir.nevercom.somu.ui.component.ExpandingText
import ir.nevercom.somu.ui.component.RatingBar
import ir.nevercom.somu.ui.theme.darkRed
import ir.nevercom.somu.ui.theme.lightOrange
import ir.nevercom.somu.util.ViewState

@Composable
fun ShowDetailsScreen(
    viewModel: ShowDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onPersonClicked: (Int) -> Unit
) {
    val state = viewModel.state.observeAsState(ShowDetailsViewState.Empty)

    Crossfade(targetState = state.value.details) { showState ->
        when (showState) {
            is ViewState.Loaded -> {
                ShowDetailsScreen(
                    show = showState.data!!,
                    cast = state.value.cast,
                    crew = state.value.crew,
                    ratings = state.value.ratings,
                    episodes = state.value.episodes,
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
internal fun ShowDetailsScreen(
    show: TmdbShow,
    cast: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>>,
    crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    ratings: ViewState<List<TmdbContentRating>>,
    episodes: ViewState<Map<Int, List<TmdbEpisode.Slim>>>,
    onBackClicked: () -> Unit,
    onPersonClicked: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        Background(show.poster?.get(TmdbImage.Quality.POSTER_W_185))

        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(show = show, onBackClicked = onBackClicked)
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
            ) {
                PosterSection(
                    show = show,
                    crew = crew,
                    ratings = ratings,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                CastsSection(
                    casts = cast,
                    onClick = { onPersonClicked(it.id) }
                )

                show.overview.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Storyline",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ExpandingText(
                        text = show.overview,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )
                }
                SeasonSection(episodes, show)
            }

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SeasonSection(
    episodes: ViewState<Map<Int, List<TmdbEpisode.Slim>>>,
    show: TmdbShow
) {
    if (episodes is ViewState.Loaded) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Seasons",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        show.seasons.forEach { season ->
            Spacer(modifier = Modifier.height(16.dp))

            var visible by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray.copy(alpha = 0.25f))
                        .clickable {
                            visible = visible.not()
                        }
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${season.title} (${season.episodeCount} episodes)",
                        style = MaterialTheme.typography.body2,
                    )
                    Text(
                        text = "Aired: ${season.airDate?.localize()}",
                        style = MaterialTheme.typography.caption
                    )
                }
                val episodeList = episodes.data!![season.seasonId]!!
                AnimatedVisibility(visible = visible) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray.copy(alpha = 0.1f))
                    ) {
                        Divider(color = Color.White, thickness = 1.dp)
                        for (item in episodeList) {
                            Text(
                                text = "${item.episodeNumber}. ${item.title}",
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(8.dp)

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Background(url: String?) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(true)
                transformations(BlurTransformation(LocalContext.current, 15f, 3f))
            }
        ),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7f),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun TopBar(show: TmdbShow, onBackClicked: () -> Unit) {
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
            text = show.title,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
private fun CastsSection(
    casts: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>>,
    onClick: (cast: TmdbPerson.Slim) -> Unit
) {
    Text(
        text = "The Cast",
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        when (casts) {
            is ViewState.Loaded -> {
                items(
                    items = casts.data!!.filter { it.first.profile != null }.take(10)
                ) { cast ->
                    CastCard(
                        profileUrl = cast.first.profile?.get(TmdbImage.Quality.PROFILE_W_154),
                        name = cast.first.name,
                        onClick = { onClick(cast.first) }
                    )
                }
            }
            else -> {
                items(10) {
                    CastPlaceHolder()
                }
            }
        }
    }
}

@Composable
private fun PosterSection(
    modifier: Modifier = Modifier,
    show: TmdbShow,
    crew: ViewState<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>>,
    ratings: ViewState<List<TmdbContentRating>>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Poster(show.poster?.get(TmdbImage.Quality.POSTER_W_500))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "${show.numberOfSeasons} Seasons (${show.numberOfEpisodes} episodes)",
                style = MaterialTheme.typography.caption
            )

            CertificationAndDate(ratings, show)

            show.genres.let { genres ->

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    genres.take(3).forEach {
                        Pill(it.name)
                    }
                }

            }

            Rating(show)

            Text(
                text = "Runtime: ${show.runtime} minutes",
                style = MaterialTheme.typography.caption,
            )

            if (crew is ViewState.Loaded) {
                val creator = crew.data?.find { it.second.job.lowercase() == "creator" }
                val director = creator?.first?.name
                    ?: (crew.data?.find { it.second.department.lowercase() == "Directing" }?.first?.name
                        ?: "????")
                Text(
                    text = "Directed by $director",
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
private fun Poster(url: String?) {
    Image(
        painter = rememberImagePainter(
            data = url,
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
}

@Composable
private fun Pill(text: String) {
    Text(
        text = text,
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

@Composable
private fun Rating(show: TmdbShow) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RatingBar(
            rating = (show.voteAverage / 2).toFloat(),
            modifier = Modifier.height(20.dp),
            color = lightOrange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "(${show.voteAverage})",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
private fun CertificationAndDate(
    ratings: ViewState<List<TmdbContentRating>>,
    show: TmdbShow
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val certification = when (ratings) {
            is ViewState.Loaded -> {
                val data = ratings.data
                if (data != null && data.isNotEmpty()) {
                    val cert = data.find { it.countryCode == "US" }?.rating
                    cert ?: data.first().rating
                } else {
                    "N/A"
                }

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

        val firstDate = show.firstAirDate?.date
        val lastDate = show.latestAirDate?.date

        val releaseDate = "${firstDate?.year} - ${lastDate?.year} (${
            show.currentStatus.name.lowercase().replaceFirstChar { it.uppercase() }
        })"
        Text(
            text = releaseDate,
            style = MaterialTheme.typography.caption,
        )
    }
}