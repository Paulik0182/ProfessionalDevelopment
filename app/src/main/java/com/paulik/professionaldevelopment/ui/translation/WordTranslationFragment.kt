package com.paulik.professionaldevelopment.ui.translation

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.paulik.professionaldevelopment.ui.utils.isOnline
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class WordTranslationFragment : ViewBindingWordTranslationFragment<FragmentWordTranslationBinding>(
    FragmentWordTranslationBinding::inflate
) {

    /** Так выглядит иньекция в поле. Инжект в конструктор запрещенно, запрещено реализацией андройд */
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override lateinit var model: WordTranslationViewModel

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

                Toast.makeText(requireContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(requireActivity().application)
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /** AndroidSupportInjection - сам решает где взять компонент чтобы можно было
         * инжектить в данный класс.
         * Главный минус такого подхода в том, что он не подходит к много модульным проктам
         * (очень много генерирует кода от которого как правило хотят избавится).*/
        AndroidSupportInjection.inject(this)

        super.onViewCreated(view, savedInstanceState)

        /** создаем конкретную viewModel*/
        model = viewModelFactory.create(WordTranslationViewModel::class.java)
        model.subscribe().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })

        binding.searchFab.setOnClickListener(fabClickListener)
        binding.mainRecyclerView.layoutManager =
            LinearLayoutManager(context)
        binding.mainRecyclerView.adapter = adapter
    }

    /** реализация обстрактного метода из BaseFragment (ViewApp) */
    override fun renderData(appState: AppState) {
        when (appState) {
            /** Если Success, загружаем данные , также можем показать ошибку*/
            is AppState.Success -> {
                showViewWorking()
                val dataEntity = appState.data
                if (dataEntity.isNullOrEmpty()) {
                    showAlertDialog(
                        getString(R.string.dialog_tittle_sorry),
                        getString(R.string.empty_server_response_on_success)
                    )
                } else {
                    adapter.setData(dataEntity)
                }
            }
            is AppState.Empty -> {
                showViewWorking()
                showAlertDialog(getString(R.string.no_data_available), AppState.Empty.toString())
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.horizontalProgressBar.visibility = VISIBLE
                    binding.roundProgressBar.visibility = GONE
                    binding.horizontalProgressBar.progress = appState.progress
                } else {
                    binding.horizontalProgressBar.visibility = GONE
                    binding.roundProgressBar.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_textview_stub), appState.error.message)
            }
        }
    }

    private fun showViewWorking() {
        binding.loadingFrameLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.loadingFrameLayout.visibility = VISIBLE
    }

    companion object {

        @JvmStatic
        fun newInstance() = WordTranslationFragment()
    }
}