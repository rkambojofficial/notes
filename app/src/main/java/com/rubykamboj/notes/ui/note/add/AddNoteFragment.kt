package com.rubykamboj.notes.ui.note.add

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rubykamboj.notes.R
import com.rubykamboj.notes.databinding.FragmentAddNoteBinding
import com.rubykamboj.notes.util.hideKeyboard
import com.rubykamboj.notes.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private val viewModel: AddNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.done_item) {
            addNote()
            true
        } else {
            false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveInstanceState()
    }

    private fun addNote() {
        val title = viewModel.titleObservable.get()
        val content = viewModel.contentObservable.get()
        if (title.isNullOrBlank()) {
            return showSnackBar(R.string.title_is_required)
        }
        if (content.isNullOrBlank()) {
            return showSnackBar(R.string.content_is_required)
        }
        hideKeyboard()
        viewModel.addNote(title, content) {
            showSnackBar(R.string.note_added_successfully)
            findNavController().navigateUp()
        }
    }
}