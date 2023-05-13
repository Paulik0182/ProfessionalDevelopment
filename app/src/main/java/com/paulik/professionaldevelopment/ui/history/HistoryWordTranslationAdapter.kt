package com.paulik.professionaldevelopment.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paulik.professionaldevelopment.R
import com.paulik.professionaldevelopment.domain.entity.DataEntity

class HistoryWordTranslationAdapter(
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<HistoryWordTranslationAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataEntity> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataEntity) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.header_history_textview_recycler_item).text =
                    data.text
                itemView.setOnClickListener {
                    //TODO Обработка нажатия на элемент в списке
                    openInNewWindow(data)
                }
            }

            itemView.setOnLongClickListener {
                onListItemClickListener.onDeleteClick(itemView, data.text!!)
                true
            }
        }
    }

    private fun openInNewWindow(listItemData: DataEntity) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataEntity)
        fun onDeleteClick(view: View, word: String)
    }
}
