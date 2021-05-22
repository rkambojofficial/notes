package com.rubykamboj.notes.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rubykamboj.notes.util.date
import com.rubykamboj.notes.util.millis
import kotlinx.parcelize.Parcelize

@Entity(tableName = "note")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long = millis,
    val updatedAt: Long = createdAt
) : Parcelable {

    val addedOn: String get() = date(createdAt)
    val updatedOn: String get() = date(updatedAt)
}