/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.nevercom.somu.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import ir.nevercom.somu.util.noRippleClickable

@Composable
fun ExpandingText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    textAlign: TextAlign? = null,
    expandable: Boolean = true,
    collapsedMaxLines: Int = 4,
    expandedMaxLines: Int = Int.MAX_VALUE,
) {
    var canTextExpand by remember(text) { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = text,
            style = textStyle,
            textAlign = textAlign,
            overflow = TextOverflow.Ellipsis,
            maxLines = if (expanded) expandedMaxLines else collapsedMaxLines,
            modifier = Modifier
                .noRippleClickable(
                    enabled = expandable && canTextExpand,
                    onClick = { expanded = !expanded }
                )
                .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy))
                .then(modifier),
            onTextLayout = {
                if (!expanded) {
                    canTextExpand = it.hasVisualOverflow
                }
            }
        )
        if (!expanded) {
            Icon(
                imageVector = Icons.Filled.MoreHoriz,
                contentDescription = "Click to expand or collapse",
                modifier = Modifier.align(CenterHorizontally)
            )
        }
    }
}