package ir.nevercom.somu.ui.screen.person

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import de.vkay.api.tmdb.models.*
import de.vkay.api.tmdb.models.TmdbImage.Quality
import ir.nevercom.somu.R
import ir.nevercom.somu.ui.component.ExpandingText
import ir.nevercom.somu.ui.component.ExtendedMovieCard
import ir.nevercom.somu.ui.component.SocialMedia
import ir.nevercom.somu.ui.component.SocialMediaItem
import ir.nevercom.somu.util.ViewState

@Composable
fun PersonScreen(
    vm: PersonScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onShowClicked: (showId: Int) -> Unit
) {
    val state = vm.state.observeAsState()
    val currentState = state.value!!

    currentState.filmsCrew.data?.sortedBy { it.second.department }
    Crossfade(targetState = currentState.details) { detailsState ->
        when (detailsState) {
            is ViewState.Loaded -> {
                Content(
                    details = currentState.details.data!!,
                    externalIds = currentState.externalIds,
                    filmsCast = currentState.filmsCast,
                    filmsCrew = currentState.filmsCrew,
                    onBackClicked = onBackClicked,
                    onMovieClicked = onMovieClicked,
                    onShowClicked = onShowClicked,
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
    details: TmdbPerson,
    externalIds: ViewState<TmdbExternalIds>,
    filmsCast: ViewState<List<Pair<MediaTypeItem, TmdbPerson.CastRole>>>,
    filmsCrew: ViewState<List<Pair<MediaTypeItem, TmdbPerson.CrewJob>>>,
    onBackClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit,
    onShowClicked: (showId: Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        Image(
            painter = rememberImagePainter(
                data = details.profile?.get(Quality.PROFILE_W_154),
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
            TopBar(details = details, onBackClicked = onBackClicked)
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
            ) {
                SummarySection(
                    details = details,
                    externalIds = externalIds
                )
                if (details.biography.isNotEmpty()) {
                    BioSection(details)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Filmography",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                if (filmsCast is ViewState.Loaded) {
                    FilmsSection(
                        filmList = filmsCast.data!!,
                        onMovieClicked = onMovieClicked,
                        onShowClicked = onShowClicked,
                    )
                }
                if (filmsCrew is ViewState.Loaded) {
                    val group = filmsCrew.data?.groupBy { it.second.department }
                    group?.forEach {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = it.key,
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
                            items(items = it.value) {
                                when (it.first) {
                                    is TmdbMovie.Slim -> {
                                        val item = it.first as TmdbMovie.Slim
                                        ExtendedMovieCard(
                                            url = item.poster?.get(Quality.POSTER_W_185),
                                            rating = (item.voteAverage / 2).toFloat(),
                                            tag = it.first.mediaType.name,
                                            details = it.second.job,
                                            onClick = { onMovieClicked(item.id) }
                                        )
                                    }
                                    is TmdbShow.Slim -> {
                                        val item = it.first as TmdbShow.Slim
                                        ExtendedMovieCard(
                                            url = item.poster?.get(Quality.POSTER_W_185),
                                            rating = (item.voteAverage / 2).toFloat(),
                                            tag = it.first.mediaType.name,
                                            details = it.second.job,
                                            onClick = { onShowClicked(item.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilmsSection(
    filmList: List<Pair<MediaTypeItem, TmdbPerson.CastRole>>,
    onMovieClicked: (movieId: Int) -> Unit,
    onShowClicked: (showId: Int) -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Acting",
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
        items(items = filmList) {
            when (it.first) {
                is TmdbMovie.Slim -> {
                    val item = it.first as TmdbMovie.Slim
                    ExtendedMovieCard(
                        url = item.poster?.get(Quality.POSTER_W_185),
                        rating = (item.voteAverage / 2).toFloat(),
                        tag = it.first.mediaType.name,
                        details = it.second.character,
                        onClick = { onMovieClicked(item.id) }
                    )
                }
                is TmdbShow.Slim -> {
                    val item = it.first as TmdbShow.Slim
                    ExtendedMovieCard(
                        url = item.poster?.get(Quality.POSTER_W_185),
                        rating = (item.voteAverage / 2).toFloat(),
                        tag = it.first.mediaType.name,
                        details = it.second.character,
                        onClick = { onShowClicked(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BioSection(details: TmdbPerson) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Biography",
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    ExpandingText(
        text = details.biography,
        textStyle = MaterialTheme.typography.body2,
        //textAlign = TextAlign.Justify,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    )
}

@Composable
private fun TopBar(details: TmdbPerson, onBackClicked: () -> Unit) {
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
            text = details.name,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
private fun SummarySection(
    modifier: Modifier = Modifier,
    details: TmdbPerson,
    externalIds: ViewState<TmdbExternalIds>
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
                data = details.profile?.get(Quality.PROFILE_H_632),
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

            details.birthDay?.let {
                Text(
                    text = "Birthday: ${it.localize()}",
                    style = MaterialTheme.typography.caption
                )
            }
            if (details.isDead) {
                Text(
                    text = "Day of Death: ${details.deathDay?.localize()}",
                    style = MaterialTheme.typography.caption
                )
            }
            details.birthPlace?.let {
                Text(
                    text = "Birth Place: $it",
                    style = MaterialTheme.typography.caption
                )
            }
            if (externalIds is ViewState.Loaded) {
                val data = externalIds.data!!
                data.imdb?.let {
                    SocialMediaItem(handle = it, type = SocialMedia.IMDB)
                }
                data.instagram?.let {
                    SocialMediaItem(handle = it, type = SocialMedia.INSTAGRAM)
                }
                data.twitter?.let {
                    SocialMediaItem(handle = it, type = SocialMedia.TWITTER)
                }
                data.facebook?.let {
                    SocialMediaItem(handle = it, type = SocialMedia.FACEBOOK)
                }
            }

        }
    }
}