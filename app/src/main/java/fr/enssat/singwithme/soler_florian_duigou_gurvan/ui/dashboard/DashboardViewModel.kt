package fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.data.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class DashboardViewModel : ViewModel() {

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs

    private val client = OkHttpClient()

    // Make the function suspendable to be used with coroutines
    suspend fun fetchAndParseJSON() {
        withContext(Dispatchers.IO) {
            try {
                val url = "https://gcpa-enssat-24-25.s3.eu-west-3.amazonaws.com/playlist.json"
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                // Log the raw response for debugging
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("DashboardViewModel", "Raw Response: $responseBody")

                    // Check if responseBody is null or empty
                    if (responseBody.isNullOrEmpty()) {
                        Log.e("DashboardViewModel", "Received empty response body")
                    }

                    responseBody?.let {
                        val jsonArray = JSONArray(it)
                        val songList = mutableListOf<Song>()

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val song = Song(
                                name = item.getString("name"),
                                artist = item.getString("artist"),
                                locked = item.getBoolean("locked")
                            )

                            // Log the song details (for debugging)
                            Log.d("DashboardViewModel", "Parsed Song: $song")

                            songList.add(song)
                        }

                        // Post the song list to LiveData
                        _songs.postValue(songList)
                    }
                } else {
                    Log.e("DashboardViewModel", "Failed to fetch JSON: ${response.code}")
                    _songs.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("DashboardViewModel", "Error fetching JSON: ${e.message}", e)
                _songs.postValue(emptyList())
            }
        }
    }
}
