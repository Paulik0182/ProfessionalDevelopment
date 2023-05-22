package com.paulik.professionaldevelopment.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.paulik.models.AppState
import com.paulik.models.entity.DataEntity
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.LoadingLayoutBinding
import com.paulik.professionaldevelopment.ui.translation.dialog.AlertDialogFragment
import com.paulik.repository.domain.WordTranslationInteractor
import com.paulik.utils.network.isOnline

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseFragment<T : AppState, I : WordTranslationInteractor<T>> : Fragment() {

    private var binding: LoadingLayoutBinding? = null

    abstract val viewModel: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isNetworkAvailable = isOnline(requireActivity().application)
    }

    override fun onResume() {
        super.onResume()
        binding = LoadingLayoutBinding.inflate(layoutInflater)

        isNetworkAvailable = isOnline(requireActivity().application)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    override fun onPause() {
        super.onPause()
        binding = null
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }

            is AppState.Empty -> {
                showViewWorking()
                showAlertDialog(getString(R.string.no_data_available), AppState.Empty.toString())
            }

            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding?.horizontalProgressBar?.visibility = View.VISIBLE
                    binding?.roundProgressBar?.visibility = View.GONE
                    binding?.horizontalProgressBar?.progress = appState.progress!!
                } else {
                    binding?.horizontalProgressBar?.visibility = View.GONE
                    binding?.roundProgressBar?.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_textview_stub), appState.error.message)
            }
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(childFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun showViewWorking() {
        binding?.loadingFrameLayout?.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding?.loadingFrameLayout?.visibility = View.VISIBLE
    }

    private fun isDialogNull(): Boolean {
        return childFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun setDataToAdapter(data: List<DataEntity>)
}