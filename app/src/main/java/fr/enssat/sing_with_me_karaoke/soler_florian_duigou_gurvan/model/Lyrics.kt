package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model

import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.network.BASE_URL

data class Lyrics(
    val title: String,
    val author: String,
    val soundtrack: String,
    val soundtrackUrl: String,
    val lyrics: List<Pair<Float, String>>
)

fun parseLyrics(lyrics: String): Lyrics {
    val lines = lyrics.lines()
    val title = lines.joinToString(" ").substringAfter("# title").substringBefore("#").trim()
    val author = lines.joinToString(" ").substringAfter("# author").substringBefore("#").trim()
    val soundtrack = lines.joinToString(" ").substringAfter("# soundtrack").substringBefore("#").trim()
    val soundtrackUrl = "$BASE_URL/${soundtrack.substringBefore(".mp3").replaceFirstChar { it.titlecase() }}/$soundtrack"
    val parsedLyrics = lines.drop(lines.indexOfFirst { it.startsWith("# lyrics") } + 1)
        .mapNotNull { line ->
            val regex = Regex("""\{ *(\d+):(\d{2}) *\}""")
            val matches = regex.findAll(line)

            if (matches.any()) {
                try {
                    val firstTimestamp = matches.first()
                    val minutes = firstTimestamp.groupValues[1].toIntOrNull() ?: 0
                    val seconds = firstTimestamp.groupValues[2].toIntOrNull() ?: 0
                    val millis = firstTimestamp.groupValues.getOrNull(3)?.toIntOrNull() ?: 0
                    val timestamp = minutes * 60 + seconds + millis / 1000f

                    val text = line.replace(regex, "").trim()
                    if (text.isNotEmpty()) timestamp to text else null
                } catch (e: IndexOutOfBoundsException) {
                    null
                }
            } else {
                null
            }
        }

    return Lyrics(title, author, soundtrack, soundtrackUrl, parsedLyrics)
}