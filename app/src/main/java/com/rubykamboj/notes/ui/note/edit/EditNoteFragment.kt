package com.rubykamboj.notes.ui.note.edit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rubykamboj.notes.R
import com.rubykamboj.notes.databinding.FragmentEditNoteBinding
import com.rubykamboj.notes.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditNoteFragment : Fragment() {

    private lateinit var binding: FragmentEditNoteBinding
    private val arguments: EditNoteFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: EditNoteViewModel.Factory
    private val viewModel: EditNoteViewModel by viewModels {
        EditNoteViewModel.provideFactory(this, null, viewModelFactory, arguments.noteId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.done_item) {
            updateNote()
            true
        } else {
            false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveInstanceState()
    }

    private fun updateNote() {
        val title = viewModel.titleObservable.get()
        val content = viewModel.contentObservable.get()
        if (title.isNullOrBlank()) {
            return showSnackBar(R.string.title_is_required)
        }
        if (content.isNullOrBlank()) {
            return showSnackBar(R.string.content_is_required)
        }
        viewModel.updateNote(title, content) {
            showSnackBar(R.string.note_updated_successfully)
            findNavController().navigateUp()
        }
    }
}