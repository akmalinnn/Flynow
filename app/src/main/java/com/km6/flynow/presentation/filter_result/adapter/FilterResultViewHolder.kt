package com.km6.flynow.presentation.filter_result.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.km6.flynow.core.ViewHolderBinder
import com.km6.flynow.data.model.Flight
import com.km6.flynow.databinding.ActivityFilterResultBinding
import com.km6.flynow.databinding.ItemFlightBinding
import com.km6.flynow.utils.toDollarFormat

class FilterResultViewHolder(
    private val binding: ItemFlightBinding,
    private val itemClick: (Flight) -> Unit
) : ViewHolder(binding.root), ViewHolderBinder<Flight>{
    override fun bind(item: Flight) {
        item.let {
            binding.tvPrice.text = it.price.toDollarFormat()
            binding.tvAirline.text = it.airlineName
            binding.tvSeatValue.text = it.flightClass
            binding.tvArrivalTime.text = it.arrivalTime
            binding.tvDepartureTime.text = it.departureTime
            binding.tvFlightDeparture.text = it.depaturecity
            binding.tvFlightArrival.text = it.arrivalcity
        }
        binding.root.setOnClickListener {
            itemClick(item)
        }
    }
}