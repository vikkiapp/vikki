package ir.nevercom.somu.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.MediaType.*
import de.vkay.api.tmdb.models.TmdbPerson


inline fun String?.ifNotEmpty(crossinline block: (String) -> Unit) {
    if (this != null && this.isNotEmpty()) {
        block(this)
    }
}

/**
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

inline fun List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>.ifDirectorFound(crossinline block: (director: TmdbPerson.Slim) -> Unit) {
    val director = this.find { it.second.job.lowercase() == "director" }?.first
    if (director != null) {
        block(director)
    }
}

fun MediaType.toReadableString() = when (this) {
    PERSON -> "People"
    TV -> "Shows"
    MOVIE -> "Movies"
    SEASON -> "Seasons"
    EPISODE -> "Episodes"
    UNKNOWN -> "Unknown"
}