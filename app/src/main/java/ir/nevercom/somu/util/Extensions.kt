package ir.nevercom.somu.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role


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