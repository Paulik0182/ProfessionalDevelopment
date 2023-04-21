package com.paulik.professionaldevelopment.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.WordTranslationInteractorImpl

abstract class ViewBindingWordTranslationFragment<T : ViewBinding>(
    private val inflateBinding: (
        inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean
    ) -> T
) : BaseFragment<AppState, WordTranslationInteractorImpl>() {
    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}