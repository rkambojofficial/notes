package com.rubykamboj.notes.data.repository

import com.rubykamboj.notes.data.dao.NoteDao
import com.rubykamboj.notes.data.entity.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {

    suspend fun insert(note: Note): Long {
        return dao.insert(note)
    }

    suspend fun update(id: Long, title: String, content: String, updatedAt: Long): Int {
        return dao.update(id, title, content, updatedAt)
    }

    suspend fun delete(id: Long): Int {
        return dao.delete(id)
    }

    fun getNote(id: Long): Flow<Note> {
        return dao.getNote(id)
    }

    fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }
}