package com.paulik.professionaldevelopment.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.paulik.core.ViewBindingFragment
import com.paulik.professionaldevelopment.BuildConfig
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentAboutAppBinding

class AboutAppFragment : ViewBindingFragment<FragmentAboutAppBinding>(
    FragmentAboutAppBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        informationApp()
    }

    @SuppressLint("SetTextI18n")
    private fun informationApp() {
        binding.codVersionTextView.text =
            getString(R.string.version_code) + BuildConfig.VERSION_CODE
        binding.versionTextView.text = getString(R.string.version_name) + BuildConfig.VERSION_NAME
        binding.aboutAppTextView.text = getString(R.string.app_info)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutAppFragment()
    }
}