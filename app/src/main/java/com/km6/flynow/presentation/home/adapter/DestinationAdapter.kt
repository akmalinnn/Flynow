package com.km6.flynow.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.data.model.Destination

class DestinationAdapter(
    private val itemClick: (Destination) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Destination>() {
                override fun areItemsTheSame(
                    oldItem: Destination,
                    newItem: Destination,
                ): Boolean {
                    // membandingkan apakah item tersebut sama
                    return oldItem.cityDestination == newItem.cityDestination
                }

                override fun areContentsTheSame(
                    oldItem: Destination,
                    newItem: Destination,
                ): Boolean {
                    // yang dibandingkan adalah kontennya
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(items: List<Destination>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}