package com.km6.flynow.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.km6.flynow.R
import com.km6.flynow.data.model.FavoriteFlight
import com.km6.flynow.databinding.ItemCardDestinationBinding
import com.km6.flynow.utils.toCustomDateFormat
import com.km6.flynow.utils.toIDRFormat

class FavoriteListAdapter(private val itemClick: (FavoriteFlight) -> Unit) :
    RecyclerView.Adapter<FavoriteListAdapter.ItemProductViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<FavoriteFlight>() {
                override fun areItemsTheSame(
                    oldItem: FavoriteFlight,
                    newItem: FavoriteFlight,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: FavoriteFlight,
                    newItem: FavoriteFlight,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<FavoriteFlight>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemProductViewHolder {
        val binding = ItemCardDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemProductViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemProductViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemProductViewHolder(
        private val binding: ItemCardDestinationBinding,
        val itemClick: (FavoriteFlight) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: FavoriteFlight) {
            with(item) {
                binding.tvDestination.text = item.departureCity
                binding.tvMaskapai.text = item.airline
                binding.tvPrice.text = item.price.toIDRFormat()
                binding.ivDestinationsImage.load(this.image) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                binding.tvDate.text = item.departureTime.toCustomDateFormat()
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
