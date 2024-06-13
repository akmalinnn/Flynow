package com.km6.flynow.presentation.filter_result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.core.ViewHolderBinder
import com.km6.flynow.data.model.Flight
import com.km6.flynow.databinding.ItemFlightBinding

class FilterResultAdapter(
    private val itemClick: (Flight) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Flight>() {
                override fun areItemsTheSame(
                    oldItem: Flight,
                    newItem: Flight,
                ): Boolean {
                    // membandingkan apakah item tersebut sama
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Flight,
                    newItem: Flight,
                ): Boolean {
                    // yang dibandingkan adalah kontennya
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(items: List<Flight>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterResultViewHolder {
        val binding =
            ItemFlightBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FilterResultViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Flight>).bind(asyncDataDiffer.currentList[position])
    }
}