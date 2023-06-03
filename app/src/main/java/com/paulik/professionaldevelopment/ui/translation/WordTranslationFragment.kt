package com.paulik.professionaldevelopment.ui.translation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus
import com.paulik.core.ViewBindingWordTranslationFragment
import com.paulik.models.entity.DataEntity
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentWordTranslationBinding
import com.paulik.professionaldevelopment.ui.translation.dialog.SearchDialogFragment
import com.paulik.repository.convertMeaningsToString
import com.paulik.utils.network.isOnline
import com.paulik.utils.ui.viewById

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
private const val WORD_FROM_HISTORY_LIST = "WORD_FROM_HISTORY_LIST"
private const val FLAG_HISTORY_KEY = "FLAG_HISTORY_KEY"
private const val REQUEST_CODE_CONNECTIVITY_SETTINGS = 42

class WordTranslationFragment : ViewBindingWordTranslationFragment<FragmentWordTranslationBinding>(
    FragmentWordTranslationBinding::inflate
) {

    private var word: String? = null
    private var flagHistory: Boolean = false

    private lateinit var appUpdateManager: AppUpdateManager

    override lateinit var viewModel: WordTranslationViewModel

    private val mainRecyclerView by viewById<RecyclerView>(R.id.main_recycler_view)
    private val searchFab by viewById<FloatingActionButton>(R.id.search_fab)

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

                getController().openDescriptionWordTranslation(
                    data.text!!,
                    convertMeaningsToString(data.meanings!!),
                    data.meanings!![0].imageUrl
                )
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
    private val stateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            state.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }

    private fun popupSnackbarForCompleteUpdate() {
        val rootView = requireView()
        Snackbar.make(
            rootView,
            "Загружено обновление",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            word = it.getString(WORD_FROM_HISTORY_LIST)
            flagHistory = it.getBoolean(FLAG_HISTORY_KEY)
        }

        appUpdateManager = AppUpdateManagerFactory.create(requireContext())

        setHasOptionsMenu(true)
        initViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<DataEntity>) {
        adapter.setData(data)
    }

//    override fun onResume() {
//        super.onResume()
//
//        appUpdateManager
//            .appUpdateInfo
//            .addOnSuccessListener { appUpdateInfo ->
//                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                    popupSnackbarForCompleteUpdate()
//                }
//                if (appUpdateInfo.updateAvailability()
//                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
//                ) {
//                    (activity as RootActivity).appUpdateManager.startUpdateFlowForResult(
//                        appUpdateInfo,
//                        AppUpdateType.IMMEDIATE,
//                        activity as RootActivity,
//                        REQUEST_CODE_CONNECTIVITY_SETTINGS
//                    )

    //todo startUpdateFlowForResult - доступно только в активити.
    // todo нужно подумать как это сделать правильно!!!
//                }
//            }
//    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CONNECTIVITY_SETTINGS) {
            if (requestCode == Activity.RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
                val result = data?.getStringExtra("result")
                Toast.makeText(requireContext(), "Результат: $result", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Result code: $resultCode", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        /** Условие при котором видна иконка меню (от андройд 10)*/
        menu.findItem(R.id.menu_screen_settings)?.isVisible =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                getController().openHistoryFragment()
                true
            }

            R.id.menu_screen_settings -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startActivityForResult(
                        Intent(android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY),
                        REQUEST_CODE_CONNECTIVITY_SETTINGS
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Данная функция активна на Android 10 и более поздней версии",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViewModel() {
        if (mainRecyclerView.adapter != null) {
            throw IllegalStateException(getString(R.string.view_model_exception))
        }

        val viewScope: WordTranslationViewModel by scope.inject()
        viewModel = viewScope
        viewModel.subscribe().observe(viewLifecycleOwner) { appStat ->
            renderData(appStat)
        }
    }

    private fun initViews() {
        if (flagHistory) {
            viewModel.getData(word!!, true)
            searchFab.visibility = View.GONE
        }

        searchFab.setOnClickListener(fabClickListener)

        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mainRecyclerView.adapter = adapter
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