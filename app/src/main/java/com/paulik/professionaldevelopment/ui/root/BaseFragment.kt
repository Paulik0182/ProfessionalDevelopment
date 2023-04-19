package com.paulik.professionaldevelopment.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.paulik.professionaldevelopment.AppState
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.domain.repo.WordTranslationInteractor
import com.paulik.professionaldevelopment.ui.translation.dialog.AlertDialogFragment
import com.paulik.professionaldevelopment.ui.utils.isOnline

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseFragment<T : AppState, I : WordTranslationInteractor<T>> : Fragment() {

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNetworkAvailable = isOnline(requireActivity().application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isNetworkAvailable = isOnline(requireActivity().application)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(requireActivity().application)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(childFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return childFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun renderData(appState: T)
}