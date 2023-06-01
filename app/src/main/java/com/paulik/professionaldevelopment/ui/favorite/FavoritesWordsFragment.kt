package com.paulik.professionaldevelopment.ui.favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulik.core.ViewBindingFragment
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentFavoritesWordsBinding
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

private const val FAVOR_SCOPE_ID = "FAVOR_SCOPE_ID"
const val FAVOR_SCOPE_NAME = "FAVOR_SCOPE_NAME"

class FavoritesWordsFragment : ViewBindingFragment<FragmentFavoritesWordsBinding>(
    FragmentFavoritesWordsBinding::inflate
) {

    private lateinit var viewModel: FavoriteWordViewModel

    private lateinit var scopeFavorite: Scope

    private val adapter: FavoriteWordAdapter by lazy {
        FavoriteWordAdapter(
            data = emptyList(),
            viewModel = viewModel,
            context = requireContext().applicationContext,
            onWordClickListener = {
                viewModel.onWordClick(it)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initViews()

        onClickIconSearching()
    }

    private fun onClickIconSearching() {
        binding.inputLayout.setEndIconOnClickListener {
            val word = binding.inputEditText.text.toString()
            Log.d("@@@", "FavoritesWordsFragment -> onClickIconSearching: $word")

            onWhenSearchingWordList(word)
        }
    }

    private fun onWhenSearchingWordList(word: String?): Boolean {
        if (!word.isNullOrEmpty()) {
            viewModel.searchData(word)
        }
        return true
    }

    private fun initViewModel() {
        if (binding.favoriteFragmentRecyclerview.adapter != null) {
            throw IllegalStateException(getString(R.string.view_model_exception))
        }

        viewModel.favoriteEntityLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        viewModel.selectedDetailsWordLiveData.observe(viewLifecycleOwner) {
            getController().openDetailsWord(it.word, it.isFavorite)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onRefresh()
    }

    private fun initViews() {
        binding.favoriteFragmentRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.favoriteFragmentRecyclerview.adapter = adapter
    }

    interface Controller {
        fun openDetailsWord(
            word: String,
            flagFavorite: Boolean
        )
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scopeFavorite = getKoin().createScope(FAVOR_SCOPE_ID, named(FAVOR_SCOPE_NAME))

        val viewScope: FavoriteWordViewModel by scopeFavorite.inject()
        viewModel = viewScope

        getController()
    }

    override fun onDetach() {
        super.onDetach()
        scopeFavorite.close()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesWordsFragment()
    }
}