package ir.nevercom.somu.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import androidx.paging.compose.LazyPagingItems
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.MediaType.*
import de.vkay.api.tmdb.models.TmdbPerson

/**
 * Invokes the lambda if the String is not empty or null
 */
inline fun String?.ifNotEmpty(crossinline block: (String) -> Unit) {
    if (this != null && this.isNotEmpty()) {
        block(this)
    }
}

/**
 * A Modifier that makes the composable Clickable, and disables the Ripple effect.
 * Taken from: [https://stackoverflow.com/a/66839858/1686304]
 */
inline fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
    ) {
        onClick()
    }
}

/**
 * An Extension function to find the `Director` in a list of Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>
 * and invokes the passed lambda if any `Director` is found.
 */
inline fun List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>.ifDirectorFound(crossinline block: (director: TmdbPerson.Slim) -> Unit) {
    val director = this.find { it.second.job.lowercase() == "director" }?.first
    if (director != null) {
        block(director)
    }
}

/**
 * A more suitable String representation of [MediaType] enums
 */
fun MediaType.toReadableString() = when (this) {
    PERSON -> "People"
    TV -> "Shows"
    MOVIE -> "Movies"
    SEASON -> "Seasons"
    EPISODE -> "Episodes"
    UNKNOWN -> "Unknown"
}

@OptIn(ExperimentalFoundationApi::class)
public fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(count = items.itemCount) { index ->
        itemContent(items[index])
    }
}

@OptIn(ExperimentalFoundationApi::class)
public fun <T : Any> LazyGridScope.itemsIndexed(
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(index: Int, value: T?) -> Unit
) {
    items(items.itemCount) { index ->
        itemContent(index, items[index])
    }
}