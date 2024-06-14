package com.km6.flynow.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.R
import com.km6.flynow.data.model.HistoryItem
import com.km6.flynow.databinding.ItemHistoryTripBinding

class HistoryAdapter(private val itemClick: (HistoryItem) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<HistoryItem>() {
        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(items: List<HistoryItem>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.itemView.setOnClickListener {
            val item = differ.currentList[position]
            itemClick.invoke(item)
        }
    }
    override fun getItemCount(): Int = differ.currentList.size

    inner class HistoryViewHolder(
        private val binding: ItemHistoryTripBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClick.invoke(differ.currentList[position])
                }
            }
        }

        fun bind(item: HistoryItem) {
            with(binding) {
                status.text = item.status
                tvjakarta.text = item.departureCity
                tvBerangkat.text = item.departureDate
                tvJamBerangkat.text = item.departureTime
                tvMelbourne.text = item.arrivalCity
                tvKedatangan.text = item.arrivalDate
                tvJamKedatangan.text = item.arrivalTime
                tvJam.text = item.durationHours
                tvMenit.text = item.durationMinutes
                Kode.text = item.bookingCode
                Economy.text = item.flightClass
                tvPrice.text = item.price

                // Set background based on status
                when (item.status) {
                    "Paid!" -> status.background = ContextCompat.getDrawable(root.context, R.drawable.bg_status_history)
                    "Refunded" -> status.background = ContextCompat.getDrawable(root.context, R.drawable.bg_status_history)
                    "Issue!" -> status.background = ContextCompat.getDrawable(root.context, R.drawable.bg_status_history_issue)
                    else -> status.background = ContextCompat.getDrawable(root.context, R.drawable.bg_status_history)
                }
            }
        }
    }
}
