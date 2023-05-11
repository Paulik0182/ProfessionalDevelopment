package com.paulik.professionaldevelopment.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulik.professionaldevelopment.databinding.FragmentFavoritesWordsBinding
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.ui.root.ViewBindingWordTranslationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesWordsFragment : ViewBindingWordTranslationFragment<FragmentFavoritesWordsBinding>(
    FragmentFavoritesWordsBinding::inflate
) {

    override val viewModel: FavoriteWordViewModel by viewModel()

    private val adapter: FavoriteWordAdapter by lazy {
        FavoriteWordAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<DataEntity>) {
//        adapter.setData(data)
    }

    private fun initViewModel() {
        if (binding.historyFragmentRecyclerview.adapter != null) {
            throw IllegalStateException("Сначала должна быть инициализирована ViewModel")
        }
//        viewModel.subscribe().observe(viewLifecycleOwner) { appStat ->
//            renderData(appStat)
//        }
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