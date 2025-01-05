package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LyricsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val songPath = intent.getStringExtra("SONG_PATH")
        setContent {
        }
    }

    fun fetchLyrics(path: String) {
        API.restrofitService.getLyrics(path).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("songs", it)
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
}