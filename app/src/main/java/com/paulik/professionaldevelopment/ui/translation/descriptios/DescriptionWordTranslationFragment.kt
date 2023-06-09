package com.paulik.professionaldevelopment.ui.translation.descriptios

import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.paulik.core.ViewBindingFragment
import com.paulik.dialog.AlertDialogFragment
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.FragmentDescriptionWordTranslationBinding
import com.paulik.utils.network.isOnline
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"
private const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
private const val DESCRIPTION_EXTRA = "0eeb92aa-520b-4fd1-bb4b-027fbf963d9a"
private const val URL_EXTRA = "6e4b154d-e01f-4953-a404-639fb3bf7281"
private const val FLAG_FAVORITE_FRAGMENT_KEY = "FLAG_FAVORITE_FRAGMENT_KEY"

class DescriptionWordTranslationFragment :
    ViewBindingFragment<FragmentDescriptionWordTranslationBinding>(
        FragmentDescriptionWordTranslationBinding::inflate
    ) {

    private var fragmentContext: Context? = null

    private val viewModel: DescriptionWordTranslationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setActionbarHomeButtonAsUp()

        /** Это для свайпа (когда мы потянули вниз пошла реакция по обновлению данных)*/
        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener {
            startLoadingOrShowError()
        }
        setData()

//        getDataWord()
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
//            useCoilToLoadPhoto(binding.descriptionImageView, imageLink)
            useGlideToLoadPhoto(binding.descriptionImageView, imageLink)
//            usePicassoToLoadPhoto(binding.descriptionImageView, imageLink)
        }
    }

    private fun getDataWord() {
        val bundle = arguments
        val word = bundle?.getString(WORD_EXTRA)

        viewModel.wordDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is WordDetailsResult.Loading -> {
                    // todo
                }

                is WordDetailsResult.Success -> {
                    val wordDetails = result.wordDetails
                    binding.descriptionHeaderTextView.text = word
                    binding.descriptionTextView.text =
                        wordDetails.translation?.translation

                    val imageLink = wordDetails.imageUrl
                    if (imageLink.isNullOrBlank()) {
                        stopRefreshAnimationIfNeeded()
                    } else {
                        useCoilToLoadPhoto(binding.descriptionImageView, imageLink)
                    }
                }

                is WordDetailsResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
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

    /** Для тул бара который появляется когда мы свайпим*/
    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }
    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        // todo доработать - установить тул бар.
        imageView.load("https:$imageLink") {
            placeholder(R.drawable.uploading_images)
            error(R.drawable.ic_load_error_vector)
            crossfade(true)
        }
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.get()
            .load("https:$imageLink")
            .placeholder(R.drawable.uploading_images)
            .fit()
            .centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()

                    if (Build.VERSION.SDK_INT >= 31) {
                        val blurEffect = RenderEffect.createBlurEffect(
                            6f, 0f,
                            Shader.TileMode.MIRROR
                        )
                        imageView.setRenderEffect(blurEffect)
                    } else {
                        Log.d("@@@", "onSuccess() called: SDK <31")
                    }
                }

                override fun onError(e: Exception?) {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }

    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()

                    if (Build.VERSION.SDK_INT >= 31) {
                        val blurEffect = RenderEffect.createBlurEffect(
                            16f, 0f,
                            Shader.TileMode.MIRROR
                        )
                        imageView.setRenderEffect(blurEffect)
                    } else {
                        Log.d("@@@", "onSuccess() called: SDK <31")
                    }
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.uploading_images)
                    .centerCrop()
            )
            .into(imageView)
    }

    companion object {
        @JvmStatic
        fun newInstance(
            word: String,
            description: String?,
            url: String?,
            flagView: Boolean
        ) =
            DescriptionWordTranslationFragment().apply {
                arguments = Bundle().apply {
                    putString(WORD_EXTRA, word)
                    putString(DESCRIPTION_EXTRA, description)
                    putString(URL_EXTRA, url)
                    putBoolean(FLAG_FAVORITE_FRAGMENT_KEY, flagView)
                }
            }
    }
}