package com.example.square.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.square.R

class SquareRepositoriesAdapter : RecyclerView.Adapter<SquareRepositoriesViewHolder>() {
    private var data: List<SquareModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SquareRepositoriesViewHolder =
        SquareRepositoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.simple_square_layout,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SquareRepositoriesViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    fun setData(data: List<SquareModel>) {
        this.data = data
        notifyDataSetChanged()
    }
}