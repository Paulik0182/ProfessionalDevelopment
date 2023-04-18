package com.paulik.professionaldevelopment.ui.translation

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentWordTranslationBinding
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.ui.root.ViewBindingWordTranslationFragment
import com.paulik.professionaldevelopment.ui.translation.dialog.SearchDialogFragment

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class WordTranslationFragment : ViewBindingWordTranslationFragment<FragmentWordTranslationBinding>(
    FragmentWordTranslationBinding::inflate
) {

    override val model: WordTranslationViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(WordTranslationViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it) }

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
                    // Вариант 2
                    model.getData(searchWord, true)
                    // вариант 1 когда каждый раз подписываемся на Livedata чтобы получить события
//                    model.getData(searchWord, true).observe(requireActivity(), observer)
                }
            })
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
        /** Подписались один раз и обновления будут приходить каждый раз как меняются данные (с Вариантом 2)
         * Livedata содержет данные до тех пор пока ее не уничтожат*/
        model.viewState.observe(requireActivity(), observer)
    }

    /** реализация обстрактного метода из BaseFragment (ViewApp) */
    override fun renderData(appState: AppState) {
        when (appState) {
            /** Если Success, загружаем данные , также можем показать ошибку*/
            is AppState.Success -> {
                val dataEntity = appState.data
                if (dataEntity == null || dataEntity.isEmpty()) { // todo бизнес логика, не должна быть здесь (нужно вынести в презентор)
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    /** Либо обновляем данные в адаптере*/
                    showViewSuccess()
                    if (adapter == null) {
                        binding.mainRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.mainRecyclerView.adapter =
                            WordTranslationAdapter(onListItemClickListener, dataEntity)
                    } else {
                        adapter!!.setData(dataEntity)
                    }
                }
            }
            is AppState.Empty -> {
                // todo пишем новае (другое) состояние (ошибку)
                showErrorScreen(getString(R.string.no_data_available))
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.horizontalProgressBar.visibility = android.view.View.VISIBLE
                    binding.roundProgressBar.visibility = android.view.View.GONE
                    binding.horizontalProgressBar.progress = appState.progress
                } else {
                    binding.horizontalProgressBar.visibility = android.view.View.GONE
                    binding.roundProgressBar.visibility = android.view.View.VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextView.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            model.getData("hi", true).observe(this, observer)
        }
    }

    private fun showViewSuccess() {
        binding.successFrameLayout.visibility = android.view.View.VISIBLE
        binding.loadingFrameLayout.visibility = android.view.View.GONE
        binding.errorLinearLayout.visibility = android.view.View.GONE
    }

    private fun showViewLoading() {
        binding.successFrameLayout.visibility = android.view.View.GONE
        binding.loadingFrameLayout.visibility = android.view.View.VISIBLE
        binding.errorLinearLayout.visibility = android.view.View.GONE
    }

    private fun showViewError() {
        binding.successFrameLayout.visibility = android.view.View.GONE
        binding.loadingFrameLayout.visibility = android.view.View.GONE
        binding.errorLinearLayout.visibility = android.view.View.VISIBLE
    }

    companion object {

        @JvmStatic
        fun newInstance() = WordTranslationFragment()
    }
}