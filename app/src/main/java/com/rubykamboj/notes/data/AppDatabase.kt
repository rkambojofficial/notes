package com.rubykamboj.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rubykamboj.notes.data.dao.NoteDao
import com.rubykamboj.notes.data.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "Notes")
                    .fallbackToDestructiveMigration()
                    .build()
            }.also {
                instance = it
            }
        }
    }
}