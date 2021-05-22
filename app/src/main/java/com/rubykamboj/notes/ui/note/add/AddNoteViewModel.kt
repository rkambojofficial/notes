package com.rubykamboj.notes.ui.note.add

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubykamboj.notes.data.entity.Note
import com.rubykamboj.notes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: NoteRepository
) : ViewModel() {

    val titleObservable = ObservableField<String>()
    val contentObservable = ObservableField<String>()

    init {
        if (savedStateHandle.keys().size > 0) {
            val title = savedStateHandle.get<String>("title")
            val content = savedStateHandle.get<String>("content")
            titleObservable.set(title)
            contentObservable.set(content)
        }
    }

    fun addNote(title: String, content: String, callback: () -> Unit) {
        val note = Note(title = title, content = content)
        viewModelScope.launch {
            repository.insert(note)
            callback()
        }
    }

    fun saveInstanceState() {
        savedStateHandle.set("title", titleObservable.get())
        savedStateHandle.set("content", contentObservable.get())
    }
}