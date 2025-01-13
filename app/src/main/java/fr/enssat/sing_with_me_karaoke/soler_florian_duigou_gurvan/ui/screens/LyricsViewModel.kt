package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network.API
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface LyricsUiState {
    data class Success(val lyrics: String) : LyricsUiState
    object Error : LyricsUiState
    object Loading : LyricsUiState
}

class LyricsViewModel(private val lyricsPath : String) : ViewModel() {
    var lyricsUiState: LyricsUiState by mutableStateOf(LyricsUiState.Loading)
        private set

    init {
        getLyrics()
    }

    fun getLyrics() {
        viewModelScope.launch {
            lyricsUiState = LyricsUiState.Loading
            lyricsUiState = try {
                val lyrics = API.retrofitService.getLyrics(lyricsPath)
                Log.d("songs", lyrics)
                LyricsUiState.Success(
                    lyrics
                )
            } catch (e: IOException) {
                LyricsUiState.Error
            } catch (e: HttpException) {
                LyricsUiState.Error
            }
        }
    }
}