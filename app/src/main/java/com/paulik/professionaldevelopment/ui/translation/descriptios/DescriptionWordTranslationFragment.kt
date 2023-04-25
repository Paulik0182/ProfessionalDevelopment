package com.paulik.professionaldevelopment.ui.translation.descriptios

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentDescriptionWordTranslationBinding
import com.paulik.professionaldevelopment.ui.root.ViewBindingFragment
import com.paulik.professionaldevelopment.ui.translation.dialog.AlertDialogFragment
import com.paulik.professionaldevelopment.ui.utils.isOnline

private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"
private const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
private const val DESCRIPTION_EXTRA = "0eeb92aa-520b-4fd1-bb4b-027fbf963d9a"
private const val URL_EXTRA = "6e4b154d-e01f-4953-a404-639fb3bf7281"

class DescriptionWordTranslationFragment :
    ViewBindingFragment<FragmentDescriptionWordTranslationBinding>(
        FragmentDescriptionWordTranslationBinding::inflate
    ) {

    private var fragmentContext: Context? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionbarHomeButtonAsUp()

        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener {
            startLoadingOrShowError()
        }

        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // todo нормально не работает. Скорее свего отсутствует стэк
    private fun setActionbarHomeButtonAsUp() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.title_toolbar_description) // установить заголовок тулбара
        }
    }

    private fun setData() {
        val bundle = arguments
        binding.descriptionHeaderTextView.text = bundle?.getString(WORD_EXTRA)
        binding.descriptionTextView.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            useCoilToLoadPhoto(binding.descriptionImageView, imageLink)
        }
    }

    private fun startLoadingOrShowError() {
        if (isOnline(requireContext().applicationContext)) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                childFragmentManager,
                DIALOG_FRAGMENT_TAG
            )
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    // todo Какаята ошибка в коде. постоянно попадаю в error при загрузке.
//    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
//        val request = ImageRequest.Builder(imageView.context)
//            .data("https:$imageLink")
//            .placeholder(R.drawable.uploading_images)
//            .error(R.drawable.ic_load_error_vector)
//            .build()
//
//        val imageLoader = ImageLoader(imageView.context)
//        val disposable = imageLoader.enqueue(request)
//
//        imageView.doOnDetach {
//            disposable.dispose()
//        }
//
//        imageLoader.let {
//            imageView.load(request) {
//                listener(
//                    onSuccess = { request, metadata ->
//                        if (metadata is SuccessResult) {
//                            imageView.setImageDrawable(metadata.drawable)
//                        }
//                    },
//                    onError = { request, throwable ->
//                        imageView.setImageResource(R.drawable.ic_load_error_vector)
//                    }
//                )
//            }
//        }
//    }

    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        // todo доработать - установить тул бар.
        imageView.load("https:$imageLink") {
            placeholder(R.drawable.uploading_images)
            error(R.drawable.ic_load_error_vector)
            crossfade(true)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(
            context: Context,
            word: String,
            description: String,
            url: String?
        ) =
            DescriptionWordTranslationFragment().apply {
                arguments = Bundle().apply {
                    putString(WORD_EXTRA, word)
                    putString(DESCRIPTION_EXTRA, description)
                    putString(URL_EXTRA, url)
                }
                // Сохраняем контекст в свойстве класса для возможности использования в фрагменте
                this.fragmentContext = context
            }
    }
}