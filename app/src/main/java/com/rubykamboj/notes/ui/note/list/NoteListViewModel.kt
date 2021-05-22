package com.rubykamboj.notes.ui.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rubykamboj.notes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    repository: NoteRepository
) : ViewModel() {

    val notes = repository.getNotes().asLiveData()
}