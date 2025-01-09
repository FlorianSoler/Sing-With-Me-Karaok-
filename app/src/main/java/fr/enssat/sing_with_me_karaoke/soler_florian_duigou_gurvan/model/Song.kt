package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model

import kotlinx.serialization.Serializable

@Serializable
data class Song (
    val name: String,
    val artist: String,
    val path: String? = null,
    val locked: Boolean = false
)