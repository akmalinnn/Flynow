package com.km6.flynow.presentation.filter_result.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.km6.flynow.R
import com.km6.flynow.core.ViewHolderBinder
import com.km6.flynow.data.model.Flight
import com.km6.flynow.databinding.ActivityFilterResultBinding
import com.km6.flynow.databinding.ItemFlightBinding
import com.km6.flynow.utils.calculateEstimatedTime
import com.km6.flynow.utils.toCustomTimeFormat
import com.km6.flynow.utils.toDollarFormat
import com.km6.flynow.utils.toIDRFormat
import com.km6.flynow.utils.toTimeFormat

class FilterResultViewHolder(
    private val binding: ItemFlightBinding,
    private val itemClick: (Flight) -> Unit
) : ViewHolder(binding.root), ViewHolderBinder<Flight>{
    override fun bind(item: Flight) {
        item.let {
            binding.tvPrice.text = it.price.toIDRFormat()
            binding.tvAirline.text = it.airlineName
            binding.tvSeatValue.text = it.flightClass
            binding.tvArrivalTime.text = it.arrivalTime.toTimeFormat()
            binding.tvDepartureTime.text = it.departureTime.toTimeFormat()
            binding.tvFlightDeparture.text = it.depaturecity
            binding.tvFlightArrival.text = it.arrivalcity
            binding.ivAirline.load(it.image) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }




            val estimatedTime = it.departureTime.toTimeFormat()?.let { departureTime ->
                it.arrivalTime.toTimeFormat()?.let { arrivalTime ->
                    calculateEstimatedTime(departureTime, arrivalTime)
                }
            }

            binding.tvTimeSpent.text = estimatedTime
        }
        binding.root.setOnClickListener {
            itemClick(item)
        }
    }
}