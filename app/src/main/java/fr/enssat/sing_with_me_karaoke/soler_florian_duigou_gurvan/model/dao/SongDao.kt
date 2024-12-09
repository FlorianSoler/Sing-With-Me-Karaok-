package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.dao

import androidx.room.Dao
import androidx.room.Query
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.entities.Song

@Dao
interface SongDao {
    @Query("SELECT * FROM Song")
    fun getAll(): List<Song>
}