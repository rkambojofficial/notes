package com.rubykamboj.notes.util

import java.text.SimpleDateFormat
import java.util.*

val millis get() = Date().time

fun date(milliseconds: Long): String {
    return SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(milliseconds)
}