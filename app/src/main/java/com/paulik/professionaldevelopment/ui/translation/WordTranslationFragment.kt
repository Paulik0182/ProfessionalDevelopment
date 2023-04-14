package com.paulik.professionaldevelopment.ui.translation

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentWordTranslationBinding
import com.paulik.professionaldevelopment.domain.Presenter
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.ui.root.ViewApp
import com.paulik.professionaldevelopment.ui.root.ViewBindingWordTranslationFragment
import com.paulik.professionaldevelopment.ui.translation.dialog.SearchDialogFragment

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class WordTranslationFragment : ViewBindingWordTranslationFragment<FragmentWordTranslationBinding>(
    FragmentWordTranslationBinding::inflate
) {

    private var adapter: WordTranslationAdapter? = null

    private val onListItemClickListener: WordTranslationAdapter.OnListItemClickListener =
        object : WordTranslationAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataEntity) {

                Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun createPresenter(): Presenter<AppState, ViewApp> {
        return WordTranslationPresenterImpl()
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataEntity = appState.data
                if (dataEntity == null || dataEntity.isEmpty()) { // todo бизнес логика, не должна быть здесь (нужно вынести в презентор)
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding.mainActivityRecyclerview.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.mainActivityRecyclerview.adapter =
                            WordTranslationAdapter(onListItemClickListener, dataEntity)
                    } else {
                        adapter!!.setData(dataEntity)
                    }
                }
            }
            is AppState.Empty -> {
                // todo пишем новае (другое) состояние (ошибку)
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = android.view.View.VISIBLE
                    binding.progressBarRound.visibility = android.view.View.GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = android.view.View.GONE
                    binding.progressBarRound.visibility = android.view.View.VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            presenter.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = android.view.View.VISIBLE
        binding.loadingFrameLayout.visibility = android.view.View.GONE
        binding.errorLinearLayout.visibility = android.view.View.GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = android.view.View.GONE
        binding.loadingFrameLayout.visibility = android.view.View.VISIBLE
        binding.errorLinearLayout.visibility = android.view.View.GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = android.view.View.GONE
        binding.loadingFrameLayout.visibility = android.view.View.GONE
        binding.errorLinearLayout.visibility = android.view.View.VISIBLE
    }

    companion object {

        @JvmStatic
        fun newInstance() = WordTranslationFragment()
    }
}