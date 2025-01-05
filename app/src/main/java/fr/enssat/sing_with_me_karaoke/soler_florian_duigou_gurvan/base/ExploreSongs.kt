package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
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
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.entities.Song

// Define a type alias for a click listener
typealias OnSongClicked = (Song) -> Unit

class ExploreSongs {
    companion object {
        // Sample hardcoded list of songs
        val sampleSongs = listOf(
            Song(1,"Hey Jude", "The Beatles", "",true),
            Song(2,"Wake Me up When September Ends", "Green Day", "",true),
            Song(3,"Bohemian Rhapsody", "Queen", "Bohemian/Bohemian.md",false),
            Song(4,"Creep", "Radio head", "Creep/creep.md", false)
        )
    }
}

// Main composable to display the list of songs
@Composable
fun ExploreSongsScreen(
    title: String = "Explore Songs",
    songList: List<Song> = ExploreSongs.sampleSongs,
    onSongClicked: OnSongClicked
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title)
        Spacer(modifier = Modifier.height(16.dp))

        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(songList.size) { index ->
                SongItem(
                    song = songList[index],
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