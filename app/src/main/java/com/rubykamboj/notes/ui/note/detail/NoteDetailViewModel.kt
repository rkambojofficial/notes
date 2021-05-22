package com.rubykamboj.notes.ui.note.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rubykamboj.notes.data.repository.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class NoteDetailViewModel @AssistedInject constructor(
    @Assisted private val noteId: Long,
    private val repository: NoteRepository
) : ViewModel() {

    val note = repository.getNote(noteId).asLiveData()

    fun deleteNote(callback: () -> Unit) {
        viewModelScope.launch {
            repository.delete(noteId)
            callback()
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(noteId: Long): NoteDetailViewModel
    }

    companion object {

        fun provideFactory(
            factory: Factory,
            noteId: Long
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
                        return factory.create(noteId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}