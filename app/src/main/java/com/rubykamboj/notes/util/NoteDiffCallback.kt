package com.rubykamboj.notes.util

import androidx.recyclerview.widget.DiffUtil
import com.rubykamboj.notes.data.entity.Note

object NoteDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}