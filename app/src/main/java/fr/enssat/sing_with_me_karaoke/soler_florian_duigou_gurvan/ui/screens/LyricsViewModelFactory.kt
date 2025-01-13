package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LyricsViewModelFactory(private val lyricsPath : String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LyricsViewModel(lyricsPath) as T
    }
}