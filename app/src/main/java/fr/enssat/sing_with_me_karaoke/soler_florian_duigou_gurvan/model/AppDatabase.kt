package fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model

import androidx.room.Database
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.dao.SongDao
import fr.enssat.sing_with_me_karaoke.soler_florian_duigou_gurvan.model.entities.Song

@Database(entities = [Song::class], version = 1)
abstract class AppDatabase {
    abstract fun songDao(): SongDao
}