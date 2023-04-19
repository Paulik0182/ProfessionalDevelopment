package com.paulik.professionaldevelopment.ui.translation.dialog

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.SearchDialogFragmentBinding

class SearchDialogFragment : BottomSheetDialogFragment(R.layout.search_dialog_fragment) {

    private var _binding: SearchDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchDialogViewModel by lazy {
        SearchDialogViewModel()
    }

    private var onSearchClickListener: OnSearchClickListener? = null

    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchClickListener?.onClick(binding.searchInputEditText.text.toString())
            dismiss()
        }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = SearchDialogFragmentBinding.bind(view)

        initView()
    }

    private fun initView() {
        viewModel.addOnClearClickListener(
            binding.clearTextImageView,
            binding.searchInputEditText,
            binding.searchButtonTextView
        )

        viewModel.onClickSearch(
            binding.searchButtonTextView,
            onSearchButtonClickListener
        )

        binding.searchInputEditText.addTextChangedListener(
            viewModel.textWatcher(
                binding.searchInputEditText,
                binding.searchButtonTextView,
                binding.clearTextImageView
            )
        )
    }

    interface OnSearchClickListener {
        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance(): SearchDialogFragment = SearchDialogFragment()
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
        _binding = null
    }
}