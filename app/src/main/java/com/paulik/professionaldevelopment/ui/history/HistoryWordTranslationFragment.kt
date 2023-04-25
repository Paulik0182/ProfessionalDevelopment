package com.paulik.professionaldevelopment.ui.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.data.HistoryWordTranslationInteractorImpl
import com.paulik.professionaldevelopment.databinding.FragmentHistoryWordTranslationBinding
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.ui.root.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryWordTranslationFragment :
    BaseFragment<AppState, HistoryWordTranslationInteractorImpl>() {

    private var _binding: FragmentHistoryWordTranslationBinding? = null
    private val binding get() = _binding!!

    override val viewModel: HistoryWordTranslationViewModel by viewModel()
    private val adapter: HistoryWordTranslationAdapter by lazy { HistoryWordTranslationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHistoryWordTranslationBinding.bind(view)

        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataEntity>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyFragmentRecyclerview.adapter != null) {
            throw IllegalStateException("Сначала должна быть инициализирована ViewModel")
        }
        viewModel.subscribe().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
    }

    private fun initViews() {
        binding.historyFragmentRecyclerview.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryWordTranslationFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}