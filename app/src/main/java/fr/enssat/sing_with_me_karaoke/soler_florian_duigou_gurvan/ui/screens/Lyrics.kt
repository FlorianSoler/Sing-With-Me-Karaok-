package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.parseLyrics
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun LyricsScreen(lyricsUiState: LyricsUiState) {
    when(lyricsUiState) {
        is LyricsUiState.Loading -> LoadingScreen()
        is LyricsUiState.Success -> LyricsResultScreen(lyricsUiState.lyrics)
        is LyricsUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LyricsResultScreen(lyrics: String) {
    val parsedLyrics = parseLyrics(lyrics)
    Log.d("songs", parsedLyrics.toString())

    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("https://gcpa-enssat-24-25.s3.eu-west-3.amazonaws.com/DontLookBack/DontLookBack.mp3")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope {
            while (true) {
                val currentPos = exoPlayer.currentPosition / 1000f
                Log.d("songs", currentPos.toString())
                delay(1000L)
            }
        }
    }

    KaraokeSimpleTextAnimate()

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        AndroidView(
            modifier = Modifier.width(300.dp).height(200.dp).align(Alignment.Center),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                }
            },
            update = { playerView ->
                playerView.player = exoPlayer
            }
        )
    }
}

@Composable
fun KaraokeSimpleText(text: String, progress: Float) {
    var textWidth by remember { mutableIntStateOf(0) }

    Box {
        Text(
            text = text,
            color = Color.Red,
            modifier = Modifier.onSizeChanged { textWidth = it.width }
        )

        if (textWidth > 0) {
            Text(
                text = text,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier.clipToBounds().width((textWidth * progress).toInt().pxToDp())
            )
        }
    }
}

@Preview
@Composable
fun KaraokeSimpleTextAnimate(duration: Int = 7, text: String = "Is this the real life? Is this just fantasy?") {
    val karaokeAnimation = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        karaokeAnimation.animateTo(duration.toFloat(), tween(duration * 1000, easing = LinearEasing))
    }
    KaraokeSimpleText(text, karaokeAnimation.value)
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }