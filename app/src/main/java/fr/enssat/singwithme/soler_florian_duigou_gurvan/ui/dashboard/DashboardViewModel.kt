package fr.enssat.singwithme.soler_florian_duigou_gurvan.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Preview of music"
    }
    val text: LiveData<String> = _text
}