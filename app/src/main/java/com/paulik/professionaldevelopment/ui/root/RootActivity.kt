package com.paulik.professionaldevelopment.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.ActivityRootBinding
import com.paulik.professionaldevelopment.ui.settings.AboutAppFragment
import com.paulik.professionaldevelopment.ui.settings.SettingsFragment
import com.paulik.professionaldevelopment.ui.translation.WordTranslationFragment

private const val TAG_ROOT_CONTAINER_LAYOUT_KEY = "TAG_ROOT_CONTAINER_LAYOUT_KEY"

class RootActivity : ViewBindingActivity<ActivityRootBinding>(
    ActivityRootBinding::inflate
),
    SettingsFragment.Controller {

    private val wordTranslationFragment: WordTranslationFragment by lazy {
        WordTranslationFragment.newInstance()
    }
    private val settingsFragment: SettingsFragment by lazy { SettingsFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBottomNaviBar()

        if (savedInstanceState == null) {
            binding.bottomNavBar.selectedItemId = R.id.word_translation_item
        } else {
            // todo другое
        }
    }

    private fun onBottomNaviBar() {
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.word_translation_item -> {
                    navigateTo(wordTranslationFragment)
                }
                R.id.settings_item -> {
                    navigateTo(settingsFragment)
                }
                else -> throw IllegalStateException("Такого фрагмента нет")
            }
            return@setOnItemSelectedListener true
        }
    }

    // Анимация перехода между фрагментами
    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        ).replace(
            binding.fragmentContainerFrameLayout.id, fragment,
            TAG_ROOT_CONTAINER_LAYOUT_KEY
        ).commit()
    }

    // Анимация перехода между фрагментами с BackStack
    private fun navigateWithBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        ).replace(
            binding.fragmentContainerFrameLayout.id, fragment,
            TAG_ROOT_CONTAINER_LAYOUT_KEY
        )
            .addToBackStack(null)
            .commit()
    }

    override fun openAboutApp() {
        navigateWithBackStack(AboutAppFragment.newInstance())
        binding.bottomNavBar.visibility = View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        binding.bottomNavBar.visibility = View.VISIBLE
        super.onBackPressed()
    }
}