package com.km6.flynow.presentation.choose_destination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.data.model.Airport
import com.km6.flynow.databinding.ItemAirportBinding

class AirportListAdapter(private val itemClick: (Airport) -> Unit) :
    RecyclerView.Adapter<AirportListAdapter.ItemAirportViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Airport>() {
                override fun areItemsTheSame(
                    oldItem: Airport,
                    newItem: Airport
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Airport,
                    newItem: Airport
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<Airport>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAirportViewHolder {
        val binding = ItemAirportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemAirportViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ItemAirportViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemAirportViewHolder(
        private val binding: ItemAirportBinding,
        val itemClick: (Airport) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Airport) {
            with(item) {
                binding.tvAirportName.text = item.airportName
                binding.tvAirportCode.text = item.airportCode
                binding.tvAirportCity.text = item.city
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
