package com.rubykamboj.notes.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() {
    val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
}

fun Fragment.showSnackBar(@StringRes stringId: Int) {
    Snackbar.make(requireView(), stringId, Snackbar.LENGTH_SHORT).show()
}