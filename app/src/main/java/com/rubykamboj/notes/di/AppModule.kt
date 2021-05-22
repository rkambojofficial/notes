package com.rubykamboj.notes.di

import android.content.Context
import com.rubykamboj.notes.data.AppDatabase
import com.rubykamboj.notes.data.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideNoteDao(
        database: AppDatabase
    ): NoteDao {
        return database.noteDao()
    }
}