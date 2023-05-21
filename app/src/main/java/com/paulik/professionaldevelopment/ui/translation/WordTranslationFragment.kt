package com.paulik.professionaldevelopment.ui.translation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentWordTranslationBinding
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.ui.root.ViewBindingWordTranslationFragment
import com.paulik.professionaldevelopment.ui.translation.dialog.SearchDialogFragment
import com.paulik.professionaldevelopment.ui.utils.convertMeaningsToString
import com.paulik.utils.network.isOnline
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
private const val WORD_FROM_HISTORY_LIST = "WORD_FROM_HISTORY_LIST"
private const val FLAG_HISTORY_KEY = "FLAG_HISTORY_KEY"

class WordTranslationFragment : ViewBindingWordTranslationFragment<FragmentWordTranslationBinding>(
    FragmentWordTranslationBinding::inflate
) {

    private var word: String? = null
    private var flagHistory: Boolean = false

    override val viewModel: WordTranslationViewModel by viewModel()

    private val adapter: WordTranslationAdapter by lazy {
        WordTranslationAdapter(
            onListItemClickListener
        )
    }

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

    private val onListItemClickListener: WordTranslationAdapter.OnListItemClickListener =
        object : WordTranslationAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataEntity) {

                // Требуется пояснение почему так. почему meanings[0] ноль?
                getController().openDescriptionWordTranslation(
                    data.text!!,
                    convertMeaningsToString(data.meanings!!),
                    data.meanings[0].imageUrl
                )
//                Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
            }

            override fun onFavoriteClick(word: String, isFavorite: Boolean) {
                if (isFavorite) {
                    viewModel.addToFavorites(word, true)
                } else {
                    viewModel.removeFromFavorite(word, false)
                }

                Log.d(
                    "@@@",
                    "WordTranslationFragment -> onFavoriteClick: $word, $isFavorite"
                )
            }
        }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(requireActivity().application)
                if (isNetworkAvailable) {
                    viewModel.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            word = it.getString(WORD_FROM_HISTORY_LIST)
            flagHistory = it.getBoolean(FLAG_HISTORY_KEY)
        }

        setHasOptionsMenu(true)
        initViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<DataEntity>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                getController().openHistoryFragment()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViewModel() {
        if (binding.mainRecyclerView.adapter != null) {
            throw IllegalStateException("Сначала должна быть инициализирована ViewModel")
        }
        viewModel.subscribe().observe(viewLifecycleOwner) { appStat ->
            renderData(appStat)
        }
    }

    private fun initViews() {
        if (flagHistory) {
            viewModel.getData(word!!, true)
            binding.searchFab.visibility = View.GONE
        }

        binding.searchFab.setOnClickListener(fabClickListener)

        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainRecyclerView.adapter = adapter
    }

    interface Controller {
        fun openDescriptionWordTranslation(
            word: String,
            description: String?,
            url: String?
        )

        fun openHistoryFragment()
    }

    private fun getController(): Controller = activity as Controller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getController()
    }

    companion object {
        @JvmStatic
        fun newInstance(word: String, flagHistory: Boolean) =
            WordTranslationFragment().apply {
                arguments = Bundle().apply {
                    putString(WORD_FROM_HISTORY_LIST, word)
                    putBoolean(FLAG_HISTORY_KEY, flagHistory)
                }
            }
    }
}