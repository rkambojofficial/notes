package com.rubykamboj.notes.ui.note.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rubykamboj.notes.data.entity.Note
import com.rubykamboj.notes.databinding.FragmentNoteListBinding
import com.rubykamboj.notes.ui.adapters.NoteListAdapter
import com.rubykamboj.notes.util.ItemEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment(), ItemEventListener<Note> {

    private lateinit var binding: FragmentNoteListBinding
    private val viewModel: NoteListViewModel by viewModels()
    private val noteListAdapter by lazy {
        NoteListAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = noteListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addNoteButton.setOnClickListener {
            val direction = NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment()
            findNavController().navigate(direction)
        }
        viewModel.notes.observe(viewLifecycleOwner) {
            noteListAdapter.submitList(it)
        }
    }

    override fun onClick(item: Note) {
        val direction = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(item.id)
        findNavController().navigate(direction)
    }
}