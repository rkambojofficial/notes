package com.rubykamboj.notes.ui.note.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rubykamboj.notes.R
import com.rubykamboj.notes.databinding.FragmentNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private val arguments: NoteDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: NoteDetailViewModel.Factory
    private val viewModel: NoteDetailViewModel by viewModels {
        NoteDetailViewModel.provideFactory(viewModelFactory, arguments.noteId)
    }
    private val deleteNoteDialog by lazy {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(R.string.delete_note)
            setMessage(R.string.delete_note_message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        deleteNoteDialog.setPositiveButton(R.string.yes) { _, _ ->
            viewModel.deleteNote {
                findNavController().navigateUp()
            }
        }
        deleteNoteDialog.setNegativeButton(R.string.no, null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_item -> {
                val direction = NoteDetailFragmentDirections.actionNoteDetailFragmentToEditNoteFragment(arguments.noteId)
                findNavController().navigate(direction)
                true
            }
            R.id.delete_item -> {
                deleteNoteDialog.show()
                true
            }
            else -> false
        }
    }
}