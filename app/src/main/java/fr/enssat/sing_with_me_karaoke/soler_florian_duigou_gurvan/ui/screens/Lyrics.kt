package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.parseLyrics

@Composable
fun LyricsScreen(lyricsUiState: LyricsUiState) {
    when(lyricsUiState) {
        is LyricsUiState.Loading -> LoadingScreen()
        is LyricsUiState.Success -> LyricsResultScreen(lyricsUiState.lyrics.toString())
        is LyricsUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LyricsResultScreen(lyrics: String) {
    val parsedLyrics = parseLyrics(lyrics)
    Log.d("songs", parsedLyrics.toString())
    Text(parsedLyrics.toString())

    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("https://gcpa-enssat-24-25.s3.eu-west-3.amazonaws.com/DontLookBack/DontLookBack.mp3")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
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