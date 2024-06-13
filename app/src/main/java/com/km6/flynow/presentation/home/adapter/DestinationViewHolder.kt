package com.km6.flynow.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.km6.flynow.core.ViewHolderBinder
import com.km6.flynow.data.model.Destination
import com.km6.flynow.databinding.ItemCardDestinationBinding
import com.km6.flynow.utils.toDollarFormat

class DestinationViewHolder(
    private val binding: ItemCardDestinationBinding,
    private val itemClick: (Destination) -> Unit
) : ViewHolder(binding.root), ViewHolderBinder<Destination> {
    override fun bind(item: Destination) {
        item.let {
            binding.tvDestination.text = item.cityDestination
            //binding.tvDepature.text = item.cityDepature
            binding.tvDate.text = item.date
            binding.tvMaskapai.text = item.maskapai
            binding.tvPrice.text = item.price.toDollarFormat()
            binding.ivDestinationsImage.load(item.imgUrl){
                crossfade(true)
            }
        }
        binding.root.setOnClickListener {
            itemClick(item)
        }
    }
}