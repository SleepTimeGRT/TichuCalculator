package kr.minchulkim.tichucounter.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.minchulkim.tichucounter.entity.Game

@Database(entities = [Game::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun newInstance(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "tichu_counter_db"
            ).build()
        }
    }

    abstract fun gameDao(): GameDao
}