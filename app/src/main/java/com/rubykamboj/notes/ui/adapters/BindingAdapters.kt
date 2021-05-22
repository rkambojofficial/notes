package com.rubykamboj.notes.ui.adapters

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.visible(visible: Boolean) {
    isVisible = visible
}