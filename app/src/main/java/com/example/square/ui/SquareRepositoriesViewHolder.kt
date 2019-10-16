package com.example.square.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.simple_square_layout.view.*

class SquareRepositoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindView(item: SquareModel) {
        with(itemView) {
            titleView.text = item.name
            descriptionView.text = item.description
        }
    }
}