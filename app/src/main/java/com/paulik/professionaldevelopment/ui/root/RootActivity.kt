package com.paulik.professionaldevelopment.ui.root

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.paulik.core.ViewBindingActivity
import com.paulik.historyscreen.HistoryWordTranslationFragment
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.ActivityRootBinding
import com.paulik.professionaldevelopment.ui.favorite.FavoritesWordsFragment
import com.paulik.professionaldevelopment.ui.settings.AboutAppFragment
import com.paulik.professionaldevelopment.ui.settings.SettingsFragment
import com.paulik.professionaldevelopment.ui.translation.WordTranslationFragment
import com.paulik.professionaldevelopment.ui.translation.descriptios.DescriptionWordTranslationFragment

private const val TAG_ROOT_CONTAINER_LAYOUT_KEY = "TAG_ROOT_CONTAINER_LAYOUT_KEY"

class RootActivity : ViewBindingActivity<ActivityRootBinding>(
    ActivityRootBinding::inflate
),
    SettingsFragment.Controller,
    WordTranslationFragment.Controller,
    HistoryWordTranslationFragment.Controller,
    FavoritesWordsFragment.Controller {

    private val favoritesWordsFragment: FavoritesWordsFragment by lazy {
        FavoritesWordsFragment()
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
                    navigateTo(WordTranslationFragment())
                }

                R.id.history_word_translation_item -> {
                    navigateTo(HistoryWordTranslationFragment.newInstance())
                }

                R.id.favorites_word_translation_item -> {
                    navigateTo(favoritesWordsFragment)
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

    override fun openDescriptionWordTranslation(
        word: String,
        description: String?,
        url: String?
    ) {
        navigateWithBackStack(
            DescriptionWordTranslationFragment.newInstance(
                word = word,
                description = description,
                url = url,
                flagView = false
            )
        )
        binding.bottomNavBar.visibility = View.GONE
    }

    override fun openVariantTranslationWord(word: String, flagHistory: Boolean) {
        navigateWithBackStack(WordTranslationFragment.newInstance(word, flagHistory))
        binding.bottomNavBar.visibility = View.GONE
    }

    override fun openDetailsWord(word: String, flagFavorite: Boolean) {
        navigateWithBackStack(WordTranslationFragment.newInstance(word, true))
        binding.bottomNavBar.visibility = View.GONE
    }

    override fun openHistoryFragment() {
        navigateWithBackStack(
            HistoryWordTranslationFragment.newInstance()
        )
        binding.bottomNavBar.visibility = View.GONE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            binding.bottomNavBar.visibility = View.VISIBLE

            // Возвращаемся на предыдущий экран
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                // Если в стеке нет предыдущего экрана, то вызываем действие по умолчанию:
                // закрытие приложения
                return super.onKeyDown(keyCode, event)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}