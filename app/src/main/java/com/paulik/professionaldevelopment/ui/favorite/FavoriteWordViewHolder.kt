package com.paulik.professionaldevelopment.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.data.room.favorite.Favorite
import com.paulik.professionaldevelopment.databinding.HistoryRecyclerviewItemBinding

class FavoriteWordViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.history_recyclerview_item, parent, false)
) {

    private val binding: HistoryRecyclerviewItemBinding =
        HistoryRecyclerviewItemBinding.bind(itemView)

    private var favoriteImageView = itemView.findViewById<ImageView>(R.id.favorite_image_view)

    private lateinit var favoriteEntity: Favorite

    fun bind(favoriteEntity: Favorite) {
        this.favoriteEntity = favoriteEntity
        val flagFavorite = favoriteEntity.isFavorite


        if (flagFavorite) {
            binding.headerHistoryTextviewRecyclerItem.text = favoriteEntity.word
            favoriteImageView.setImageResource(R.drawable.favourites_icon_filled)
        }
    }
}