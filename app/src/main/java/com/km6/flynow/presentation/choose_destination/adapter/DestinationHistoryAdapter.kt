package com.km6.flynow.presentation.choose_destination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.data.model.Destination
import com.km6.flynow.databinding.ItemDestinationResultBinding
import com.refood.tastie.core.ViewHolderBinder

class DestinationHistoryAdapter (private val destinationHistoryListener: DestinationHistoryListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Destination>() {
                override fun areItemsTheSame(
                    oldItem: Destination,
                    newItem: Destination,
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Destination,
                    newItem: Destination,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Destination>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return CartViewHolder(
            ItemDestinationResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            destinationHistoryListener,
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Destination>).bind(dataDiffer.currentList[position])
    }
}

class CartViewHolder(
    private val binding: ItemDestinationResultBinding,
    private val destinationHistoryListener: DestinationHistoryListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Destination> {
    override fun bind(item: Destination) {
        setCartData(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Destination) {
        with(binding) {
            tvDestination.text = item.destinationName

        }
    }
    private fun setClickListeners(item: Destination) {
        with(binding) {
            ivClose.setOnClickListener { destinationHistoryListener?.onRemoveDestinationClicked(item) }
        }
    }
}

interface DestinationHistoryListener {
    fun onRemoveDestinationClicked(destination: Destination)
}