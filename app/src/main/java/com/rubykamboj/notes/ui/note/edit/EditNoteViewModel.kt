package com.rubykamboj.notes.ui.note.edit

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.rubykamboj.notes.data.entity.Note
import com.rubykamboj.notes.data.repository.NoteRepository
import com.rubykamboj.notes.util.millis
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditNoteViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    @Assisted private val noteId: Long,
    private val repository: NoteRepository
) : ViewModel() {

    private lateinit var note: Note
    val titleObservable = ObservableField<String>()
    val contentObservable = ObservableField<String>()

    init {
        if (savedStateHandle.keys().isEmpty()) {
            viewModelScope.launch {
                note = repository.getNote(noteId).first()
                titleObservable.set(note.title)
                contentObservable.set(note.content)
            }
        } else {
            val title = savedStateHandle.get<String>("title")
            val content = savedStateHandle.get<String>("content")
            titleObservable.set(title)
            contentObservable.set(content)
        }
    }

    fun updateNote(title: String, content: String, callback: () -> Unit) {
        viewModelScope.launch {
            repository.update(noteId, title, content, millis)
            callback()
        }
    }

    fun saveInstanceState() {
        savedStateHandle.set("title", titleObservable.get())
        savedStateHandle.set("content", contentObservable.get())
    }

    @AssistedFactory
    interface Factory {

        fun create(handle: SavedStateHandle, noteId: Long): EditNoteViewModel
    }

    companion object {

        fun provideFactory(
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle?,
            factory: Factory,
            noteId: Long
        ): ViewModelProvider.Factory {
            return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                    if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
                        return factory.create(handle, noteId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}