package com.paulik.professionaldevelopment.ui.translation.dialog

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText

class SearchDialogViewModel : ViewModel() {

    fun textWatcher(
        searchInputEditText: TextInputEditText,
        searchButtonTextView: TextView,
        clearTextImageView: ImageView,
    ) = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (searchInputEditText.text != null && !searchInputEditText.text.toString()
                    .isEmpty()
            ) {
                searchButtonTextView.isEnabled = true
                clearTextImageView.visibility = View.VISIBLE
            } else {
                searchButtonTextView.isEnabled = false
                clearTextImageView.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    fun addOnClearClickListener(
        clearTextImageView: ImageView,
        searchInputEditText: TextInputEditText,
        searchButtonTextView: TextView
    ) {
        clearTextImageView.setOnClickListener {
            searchInputEditText.setText("")
            searchButtonTextView.isEnabled = false
        }
    }

    fun onClickSearch(
        searchButtonTextView: TextView,
        onSearchButtonClickListener: View.OnClickListener
    ) {
        searchButtonTextView.setOnClickListener(onSearchButtonClickListener)
    }
}