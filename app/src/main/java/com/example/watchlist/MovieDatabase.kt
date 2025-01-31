package com.example.watchlist

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [MovieEntity::class], version = 5, exportSchema = false)
@TypeConverters(GenreConverter::class)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDAO

    companion object{
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        private val MIGRATION2_3 = object : Migration(2,3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE movies ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE movies ADD COLUMN isWatched INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE movies ADD COLUMN isWantToWatch INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context):MovieDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                )
                    //.addMigrations(MIGRATION2_3)
                    //.fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}