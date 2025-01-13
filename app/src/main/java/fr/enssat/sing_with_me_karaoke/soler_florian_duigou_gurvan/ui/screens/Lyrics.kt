package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
    Text(lyrics)
}