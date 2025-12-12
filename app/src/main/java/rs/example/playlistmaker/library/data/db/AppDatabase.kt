package rs.example.playlistmaker.library.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.example.playlistmaker.library.data.db.converters.Converters
import rs.example.playlistmaker.library.data.db.entity.PlayListEntity
import rs.example.playlistmaker.library.data.db.entity.TracksEntity


@Database(version = 1, entities = [TracksEntity::class, PlayListEntity::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playListDao(): PlayListDao
}
