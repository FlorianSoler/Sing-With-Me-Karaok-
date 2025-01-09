package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.Song

// Define a type alias for a click listener
typealias OnSongClicked = (Song) -> Unit

// Main composable to display the list of songs
@Composable
fun PlaylistScreen(
    title: String = "Explore Songs",
    playlistUiState: PlaylistUiState,
    onSongClicked: OnSongClicked
) {
    when(playlistUiState) {
        is PlaylistUiState.Loading -> LoadingScreen()
        is PlaylistUiState.Success -> ResultScreen(title, playlistUiState.songs, onSongClicked)
        is PlaylistUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Text("Chargement...")
}

@Composable
fun ErrorScreen() {
    Text("Erreur")
}

@Composable
fun ResultScreen(title: String, songs: List<Song>, onSongClicked: OnSongClicked) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title)
        Spacer(modifier = Modifier.height(16.dp))

        val listState = rememberLazyListState()
        Log.d("songs", songs.toString())

        LazyColumn(state = listState) {
            items(songs.size) { index ->
                SongItem(
                    song = songs[index],
                    onSongClicked = onSongClicked
                )
                HorizontalDivider(color = Color.LightGray)
            }
        }
    }
}

@Composable
fun SongItem(song: Song, onSongClicked: OnSongClicked) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSongClicked(song) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow, // Use a music note icon
            contentDescription = "Music Icon",
            tint = Color.Gray, // You can customize the icon color
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = song.name,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge // Use Material 3 style
            )
            Text(
                text = song.artist,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium // Use Material 3 style
            )
            if (song.locked) {
                Text(
                    text = "Locked",
                    color = Color.Red,
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall // Use for captions
                )
            }
        }
    }
}