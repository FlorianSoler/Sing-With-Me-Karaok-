package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.Song
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network.API
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PlaylistUiState {
    data class Success(val songs: List<Song>) : PlaylistUiState
    object Error : PlaylistUiState
    object Loading : PlaylistUiState
}

class PlaylistViewModel : ViewModel() {
    var playlistUiState: PlaylistUiState by mutableStateOf(PlaylistUiState.Loading)
        private set

    init {
        getSongs()
    }

    fun getSongs() {
        viewModelScope.launch {
            playlistUiState = PlaylistUiState.Loading
            playlistUiState = try {
                val listResult = API.retrofitService.getSongs()
                PlaylistUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                PlaylistUiState.Error
            } catch (e: HttpException) {
                PlaylistUiState.Error
            }
        }
    }
}