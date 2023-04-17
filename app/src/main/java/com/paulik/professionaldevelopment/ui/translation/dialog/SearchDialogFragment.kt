package com.paulik.professionaldevelopment.ui.translation.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paulik.professionaldevelopment.databinding.SearchDialogFragmentBinding

class SearchDialogFragment : BottomSheetDialogFragment() {

    private var _binding: SearchDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private var onSearchClickListener: OnSearchClickListener? = null

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (binding.searchInputEditText.text != null && !binding.searchInputEditText.text.toString()
                    .isEmpty()
            ) {
                binding.searchButtonTextView.isEnabled = true
                binding.clearTextImageView.visibility = View.VISIBLE
            } else {
                binding.searchButtonTextView.isEnabled = false
                binding.clearTextImageView.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchClickListener?.onClick(binding.searchInputEditText.text.toString())
            dismiss()
        }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButtonTextView.setOnClickListener(onSearchButtonClickListener)

        binding.searchInputEditText.addTextChangedListener(textWatcher)

        addOnClearClickListener()
    }

    private fun addOnClearClickListener() {
        binding.clearTextImageView.setOnClickListener {
            binding.searchInputEditText.setText("")
            binding.searchButtonTextView.isEnabled = false
        }
    }

    interface OnSearchClickListener {

        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
        _binding = null
    }
}
