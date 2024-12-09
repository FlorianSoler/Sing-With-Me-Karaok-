package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.ui.theme.SingWithMeKaraokeTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SingWithMeKaraokeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    LoadSongsButton(onClick = {
                        Log.d("Load", "Load songs clicked.")
                    })
                }
            }
        }
    }
}

@Composable
fun LoadSongsButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Load Songs")
    }
}