package com.paulik.professionaldevelopment.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.paulik.models.entity.FavoriteEntity
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.databinding.ElementWordRecyclerviewItemBinding

class FavoriteWordViewHolder(
    parent: ViewGroup,
    private val viewModel: FavoriteWordViewModel,
    private var context: Context,
    onWordClickListener: (FavoriteEntity) -> Unit = {}
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.element_word_recyclerview_item, parent, false)
) {

    private val binding: ElementWordRecyclerviewItemBinding =
        ElementWordRecyclerviewItemBinding.bind(itemView)

    private var favoriteImageView = itemView.findViewById<ImageView>(R.id.favorite_image_view)

    private lateinit var favoriteEntity: FavoriteEntity

    fun bind(favoriteEntity: FavoriteEntity) {
        this.favoriteEntity = favoriteEntity

        val word = favoriteEntity.word
        val flagFavorite = favoriteEntity.isFavorite
        binding.favoriteImageView.visibility = View.VISIBLE

        binding.headerWordTextviewRecyclerItem.text = word

        favoriteImageView.setImageResource(R.drawable.favourites_icon_filled)

        favoriteImageView.setOnClickListener {
            if (flagFavorite) {
                favoriteImageView.setImageResource(R.drawable.favourites_icon)
                viewModel.deleteWord(word)
            }
        }
    }

    init {
        itemView.setOnClickListener {
            onWordClickListener.invoke(favoriteEntity)
        }
    }
}