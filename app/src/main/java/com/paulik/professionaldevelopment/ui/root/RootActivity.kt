package com.paulik.professionaldevelopment.ui.root

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
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
private const val REQUEST_CODE_CONNECTIVITY_SETTINGS = 42

class RootActivity : ViewBindingActivity<ActivityRootBinding>(
    ActivityRootBinding::inflate
),
    SettingsFragment.Controller,
    WordTranslationFragment.Controller,
    HistoryWordTranslationFragment.Controller,
    FavoritesWordsFragment.Controller {

    private lateinit var appUpdateManager: AppUpdateManager

    private val favoritesWordsFragment: FavoritesWordsFragment by lazy {
        FavoritesWordsFragment()
    }
    private val settingsFragment: SettingsFragment by lazy { SettingsFragment.newInstance() }

    private var contentHasLoaded = true
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(this)

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

                else -> throw IllegalStateException(getString(R.string.fragment_exception))
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

    private val stateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            state.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }

    private fun popupSnackbarForCompleteUpdate() {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(
            rootView,
            "Загружено обновление",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE_CONNECTIVITY_SETTINGS
                    )
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CONNECTIVITY_SETTINGS) {
            if (requestCode == Activity.RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
                val result = data?.getStringExtra("result")
                Toast.makeText(this, "Результат: $result", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Result code: $resultCode", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}