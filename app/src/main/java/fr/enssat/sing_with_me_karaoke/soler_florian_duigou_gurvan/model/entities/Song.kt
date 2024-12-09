package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "path") val path: String?,
    @ColumnInfo(name = "locked") val locked: Boolean
)