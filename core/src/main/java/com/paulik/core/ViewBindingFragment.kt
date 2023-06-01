package com.paulik.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.koin.androidx.scope.ScopeFragment

abstract class ViewBindingFragment<T : ViewBinding>(
    private val inflateBinding: (
        inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean
    ) -> T
) : ScopeFragment() {
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