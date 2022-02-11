package ir.nevercom.somu.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.nevercom.somu.model.Converters
import ir.nevercom.somu.model.Movie
import ir.nevercom.somu.repositories.local.MovieDao

@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class VikkiDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}