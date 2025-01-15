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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.parseLyrics
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

    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(parsedLyrics.soundtrackUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    var currentLine by remember { mutableIntStateOf(-1) }
    val karaokeAnimation = remember { Animatable(0f) }
    val currentLyricText = remember(currentLine) {
        parsedLyrics.lyrics.getOrNull(currentLine)?.second ?: ""
    }

    LaunchedEffect(Unit) {
        while (true) {
            val lyricIndex = parsedLyrics.lyrics.indexOfLast { (exoPlayer.currentPosition / 1000f) >= it.first }
            //TODO: remove logs
            Log.d("songs", "currentPosition = ${exoPlayer.currentPosition / 1000f}")
            Log.d("songs", "lyricIndex = $lyricIndex")
            Log.d("songs", "lyric = $currentLyricText")

            if (lyricIndex != -1 && lyricIndex != currentLine) {
                val nextLyric = parsedLyrics.lyrics.getOrNull(lyricIndex + 1)
                val currentLyric = parsedLyrics.lyrics[lyricIndex]
                val nextTimestamp = nextLyric?.first ?: Float.MAX_VALUE

                val durationMillis = ((nextTimestamp - currentLyric.first) * 1000).toInt()

                karaokeAnimation.snapTo(0f)
                karaokeAnimation.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis, easing = LinearEasing)
                )

                currentLine = lyricIndex
            }

            delay(50)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        KaraokeSimpleText(
            modifier = Modifier.align(Alignment.Center),
            text = currentLyricText,
            progress = karaokeAnimation.value
        )

        AndroidView(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .align(Alignment.BottomCenter),
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
fun KaraokeSimpleText(modifier: Modifier, text: String, progress: Float) {
    var textWidth by remember { mutableIntStateOf(0) }

    Box(modifier) {
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
                modifier = Modifier
                    .clipToBounds()
                    .width(
                        (textWidth * progress)
                            .toInt()
                            .pxToDp()
                    )
            )
        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }