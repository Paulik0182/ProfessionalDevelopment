package com.paulik.professionaldevelopment.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.paulik.professionaldevelopment.BuildConfig
import com.paulik.professionaldevelopment.databinding.FragmentAboutAppBinding
import com.paulik.professionaldevelopment.ui.root.ViewBindingFragment

class AboutAppFragment : ViewBindingFragment<FragmentAboutAppBinding>(
    FragmentAboutAppBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        informationApp()
    }

    @SuppressLint("SetTextI18n")
    private fun informationApp() {
        binding.codVersionTextView.text = "Код версии: " + BuildConfig.VERSION_CODE
        binding.versionTextView.text = "Версия: " + BuildConfig.VERSION_NAME
        binding.aboutAppTextView.text =
            "О Приложении\n\nПриложение является результатом выполнения " +
                    "практических заданий по освоению разработки Android приложений." +
                    "\n\nКурс - Профессиональная разработка Android-приложений\n(Апрель 2023 года)"
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutAppFragment()
    }
}