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
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.base.ExploreSongsScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.entities.Song
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network.API
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.theme.SingWithMeKaraokeTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var songs: List<Song> = emptyList()

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fetchSongs()
        setContent {
            SingWithMeKaraokeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ExploreSongsScreen(songList = songs, onSongClicked = { song ->
                        // Handle song click
                        Log.d("songs", "CLICKEDDDDDDDDDDDDDDDDDDDDDD")
                    })
                }
            }
        }
    }
}

fun fetchSongs() {
    API.restrofitService.getSongs().enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    songs = parseJson(it)
                }
            } else {
                Log.d("songs", response.code().toString())
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            t.message?.let { it1 -> Log.d("songs", it1) }
        }
    })
}

fun parseJson(json: String): List<Song> {
    val gson = Gson()
    val type = object : TypeToken<List<Song>>() {}.type
    return gson.fromJson(json, type)
}
