package com.rubykamboj.notes.util

interface ItemEventListener<in T : Any> {

    fun onClick(item: T)
}