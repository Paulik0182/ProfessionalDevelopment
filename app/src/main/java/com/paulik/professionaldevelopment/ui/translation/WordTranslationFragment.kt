package com.paulik.professionaldevelopment.ui.translation

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentWordTranslationBinding
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.ui.root.ViewBindingWordTranslationFragment
import com.paulik.professionaldevelopment.ui.translation.dialog.SearchDialogFragment
import com.paulik.professionaldevelopment.ui.utils.convertMeaningsToString
import com.paulik.professionaldevelopment.ui.utils.isOnline
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class WordTranslationFragment : ViewBindingWordTranslationFragment<FragmentWordTranslationBinding>(
    FragmentWordTranslationBinding::inflate
) {

    private lateinit var recyclerView: RecyclerView

    override val viewModel: WordTranslationViewModel by viewModel()

    private val adapter: WordTranslationAdapter by lazy {
        WordTranslationAdapter(
            onListItemClickListener
        )
    }

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

    private val onListItemClickListener: WordTranslationAdapter.OnListItemClickListener =
        object : WordTranslationAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataEntity) {

                // Требуется пояснение почему так. почему meanings[0] ноль?
                getController().openDescriptionWordTranslation(
                    requireActivity(),
                    data.text!!,
                    convertMeaningsToString(data.meanings!!),
                    data.meanings[0].imageUrl
                )
//                Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
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

        recyclerView = view.findViewById(R.id.main_recycler_view)
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
        binding.searchFab.setOnClickListener(fabClickListener)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainRecyclerView.adapter = adapter

        recyclerView.itemAnimator = NoAnimationItemAnimator()
//        recyclerView.itemAnimator?.changeDuration = 0
//        recyclerView.itemAnimator?.addDuration = 0
//        recyclerView.itemAnimator?.removeDuration = 0
//        recyclerView.itemAnimator?.moveDuration = 0
//        recyclerView.itemAnimator = null
    }

    interface Controller {
        fun openDescriptionWordTranslation(
            context: Context,
            word: String,
            description: String,
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
        fun newInstance() = WordTranslationFragment()
    }
}