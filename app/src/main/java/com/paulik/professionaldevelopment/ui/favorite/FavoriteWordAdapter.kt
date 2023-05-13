package com.paulik.professionaldevelopment.ui.favorite

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulik.professionaldevelopment.domain.entity.FavoriteEntity

class FavoriteWordAdapter(
    private var data: List<FavoriteEntity> = mutableListOf(),
    private var viewModel: FavoriteWordViewModel
) : RecyclerView.Adapter<FavoriteWordViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(favoriteEntity: List<FavoriteEntity>) {
        data = favoriteEntity
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteWordViewHolder {
        return FavoriteWordViewHolder(
            parent,
            viewModel
        )
    }

    override fun onBindViewHolder(holder: FavoriteWordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): FavoriteEntity = data[position]

    override fun getItemCount(): Int = data.size
}