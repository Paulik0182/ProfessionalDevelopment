package com.paulik.professionaldevelopment.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulik.professionaldevelopment.databinding.FragmentFavoritesWordsBinding
import com.paulik.professionaldevelopment.ui.root.ViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesWordsFragment : ViewBindingFragment<FragmentFavoritesWordsBinding>(
    FragmentFavoritesWordsBinding::inflate
) {

    private val viewModel: FavoriteWordViewModel by viewModel()

    private val adapter: FavoriteWordAdapter by lazy {
        FavoriteWordAdapter(
            data = emptyList(),
            viewModel = viewModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        if (binding.historyFragmentRecyclerview.adapter != null) {
            throw IllegalStateException("Сначала должна быть инициализирована ViewModel")
        }
        viewModel.favoriteEntityLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onRefresh()
    }

    private fun initViews() {
        binding.historyFragmentRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.historyFragmentRecyclerview.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesWordsFragment()
    }
}