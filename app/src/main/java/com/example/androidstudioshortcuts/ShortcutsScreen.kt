package com.example.androidstudioshortcuts

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.androidstudioshortcuts.data.Datasource
import com.example.androidstudioshortcuts.model.Shortcut
import com.example.androidstudioshortcuts.ui.theme.AndroidStudioShortcutsTheme

@Composable
fun ShortcutTopAppBar(modifier: Modifier = Modifier) {
    Surface() {
        Row(
            modifier = modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.shortcuts_logo_top),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShortcutList(shortcuts: List<Shortcut>, modifier: Modifier = Modifier) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)),
        exit = fadeOut()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier.padding(10.dp)
        ) {
            itemsIndexed(shortcuts) { index, shortcut ->
                ShortcutItem(shortcut = shortcut, modifier = Modifier.animateEnterExit(
                    enter = slideInVertically(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessVeryLow,
                            dampingRatio = Spring.DampingRatioLowBouncy
                        ),
                        initialOffsetY = { it * (index + 1) } // staggered entrance
                    )
                ))
            }
        }
    }
}

@Composable
fun ShortcutItem(shortcut: Shortcut, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 64.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = shortcut.shortcutResId),
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = stringResource(id = shortcut.actionResId),
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                ShortcutItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if (expanded) {
                ShortcutGIF(shortcut.gifResId)
            }
        }
    }
}

@Composable
fun ShortcutItemButton(
    expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(id = R.string.expand_button_content_description),
            tint = MaterialTheme.colors.secondary
        )
    }
}

@Composable
fun ShortcutGIF(@DrawableRes shortcutGif: Int, modifier: Modifier = Modifier) {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        if (SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()

    Box(modifier = modifier.padding(top = 8.dp)) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = shortcutGif).apply {
                    size(
                        Size.ORIGINAL
                    )
                }.build(), imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.FillWidth
        )
    }

}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShortcutItemPreview() {
    AndroidStudioShortcutsTheme {
        ShortcutItem(shortcut = Shortcut(R.string.shortcut1, R.string.action1, R.drawable.ctrl_y))
    }
}

@Preview
@Composable
fun ShortcutListPreview() {
    AndroidStudioShortcutsTheme {
        ShortcutList(shortcuts = Datasource.shortcuts)
    }
}

@Preview
@Composable
fun ShortcutTopAppBarPreview() {
    AndroidStudioShortcutsTheme {
        ShortcutTopAppBar()
    }
}
