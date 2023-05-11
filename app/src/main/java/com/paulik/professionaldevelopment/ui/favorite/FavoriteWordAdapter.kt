package com.paulik.professionaldevelopment.ui.favorite

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulik.professionaldevelopment.data.room.favorite.Favorite

class FavoriteWordAdapter(
    private var data: List<Favorite> = mutableListOf()
) : RecyclerView.Adapter<FavoriteWordViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(favoriteEntity: List<Favorite>) {
        data = favoriteEntity
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteWordViewHolder {
        return FavoriteWordViewHolder(
            parent
        )
    }

    override fun onBindViewHolder(holder: FavoriteWordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Favorite = data[position]

    override fun getItemCount(): Int = data.size
}