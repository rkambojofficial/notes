package com.rubykamboj.notes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rubykamboj.notes.data.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note): Long

    @Query("UPDATE note SET title = :title, content = :content, updatedAt = :updatedAt WHERE id = :id;")
    suspend fun update(id: Long, title: String, content: String, updatedAt: Long): Int

    @Query("DELETE FROM note WHERE id = :id;")
    suspend fun delete(id: Long): Int

    @Query("SELECT * FROM note WHERE id = :id LIMIT 1;")
    fun getNote(id: Long): Flow<Note>

    @Query("SELECT * FROM note ORDER BY updatedAt DESC;")
    fun getNotes(): Flow<List<Note>>
}