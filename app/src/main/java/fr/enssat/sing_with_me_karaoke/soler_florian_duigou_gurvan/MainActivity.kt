package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.Song
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens.LyricsScreen
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens.LyricsViewModel
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens.LyricsViewModelFactory
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens.PlaylistScreen
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens.PlaylistViewModel
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.theme.SingWithMeKaraokeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SingWithMeKaraokeTheme {
                SingWithMeKaraokeApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun SingWithMeKaraokeApp(modifier: Modifier = Modifier) {

    var lyricsToShow by remember { mutableStateOf<Song?>(null) }

    Surface(modifier) {
        if (lyricsToShow != null) {
            val lyricsViewModel: LyricsViewModel = viewModel(
                factory = lyricsToShow!!.path?.let { LyricsViewModelFactory(it) }
            )
            LyricsScreen(lyricsUiState = lyricsViewModel.lyricsUiState)
        } else {
            val playlistViewModel: PlaylistViewModel = viewModel()
            PlaylistScreen(
                playlistUiState = playlistViewModel.playlistUiState,
                onSongClicked = { song -> lyricsToShow = song }
            )
        }
    }
}
