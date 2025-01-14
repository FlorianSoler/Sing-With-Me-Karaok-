package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model

import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network.BASE_URL

data class Lyrics(
    val title: String,
    val author: String,
    val soundtrack: String,
    val soundtrackUrl: String
    // val lyrics: List<Pair<String, Float>>
)

fun parseLyrics(lyrics: String): Lyrics {
    val lines = lyrics.lines()
    val title = lines.joinToString(" ").substringAfter("# title").substringBefore("#").trim()
    val author = lines.joinToString(" ").substringAfter("# author").substringBefore("#").trim()
    val soundtrack = lines.joinToString(" ").substringAfter("# soundtrack").substringBefore("#").trim()
    val soundtrackUrl = "$BASE_URL/${soundtrack.substringBefore(".mp3").replaceFirstChar { it.titlecase() }}/$soundtrack"
    /* val parsedLyrics = lines.filter { it.startsWith("{") }
        .map {
            val timestamp = it.substringAfter("{ ").substringBefore(" }").toFloat()
            val text = it.substringAfter("}").trim()
            text to timestamp
        } */
    // return Lyrics(title, author, soundtrack, parsedLyrics)
    return Lyrics(title, author, soundtrack, soundtrackUrl)
}