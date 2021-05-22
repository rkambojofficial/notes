package com.rubykamboj.notes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rubykamboj.notes.data.entity.Note
import com.rubykamboj.notes.databinding.ItemNoteBinding
import com.rubykamboj.notes.util.ItemEventListener
import com.rubykamboj.notes.util.NoteDiffCallback

class NoteListAdapter(
    private val eventListener: ItemEventListener<Note>
) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(
            ItemNoteBinding.inflate(inflater, parent, false),
            eventListener
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val eventListener: ItemEventListener<Note>
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var note: Note

        init {
            itemView.setOnClickListener {
                eventListener.onClick(note)
            }
        }

        fun bind(item: Note) {
            note = item
            binding.note = item
            binding.executePendingBindings()
        }
    }
}