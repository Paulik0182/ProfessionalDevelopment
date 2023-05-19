package com.paulik.professionaldevelopment.ui.translation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.domain.entity.DataEntity
import com.paulik.professionaldevelopment.ui.utils.convertMeaningsToString

class WordTranslationAdapter(
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<WordTranslationAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataEntity> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.word_translation_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private var flagFavorite: Boolean = true
        private var favoriteImageView = itemView.findViewById<ImageView>(R.id.favorite_image_view)

        fun bind(data: DataEntity) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.header_recycler_item_text_view).text =
                    data.text
                itemView.findViewById<TextView>(R.id.description_recycler_item_text_view).text =
                    convertMeaningsToString(data.meanings!!)
                itemView.setOnClickListener { openInNewWindow(data) }
            }

            favoriteImageView.setOnClickListener {
                onListItemClickListener.onFavoriteClick(
                    data.text!!,
                    flagFavorite
                )

                if (flagFavorite) {
                    favoriteImageView.setImageResource(R.drawable.favourites_icon_filled)
                    flagFavorite = false
                } else {
                    favoriteImageView.setImageResource(R.drawable.favourites_icon)
                    flagFavorite = true
                }
            }
        }
    }

    private fun openInNewWindow(listItemData: DataEntity) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataEntity)

        fun onFavoriteClick(word: String, isFavorite: Boolean)
    }
}