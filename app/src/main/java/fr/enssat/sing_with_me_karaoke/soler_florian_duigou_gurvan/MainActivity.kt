package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens.PlaylistScreen
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens.PlaylistViewModel
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.theme.SingWithMeKaraokeTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SingWithMeKaraokeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val playlistViewModel: PlaylistViewModel = viewModel()
                    PlaylistScreen(playlistUiState = playlistViewModel.playlistUiState, onSongClicked = { song ->
                        Log.d("songs", song.toString())
                        // val intent = Intent(this, LyricsActivity::class.java)
                        // intent.putExtra("SONG_PATH", song.path)
                        // startActivity(intent)
                    })
                }
            }
        }
    }
}
