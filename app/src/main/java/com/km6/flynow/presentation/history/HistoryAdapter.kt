package com.km6.flynow.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.km6.flynow.R
import com.km6.flynow.data.model.history.History
import com.km6.flynow.databinding.ItemHistoryTripBinding
import com.km6.flynow.utils.calculateEstimatedTime
import com.km6.flynow.utils.toCustomDateFormat
import com.km6.flynow.utils.toCustomTimeFormat
import com.km6.flynow.utils.toIDRFormat

class HistoryAdapter(private val itemClick: (History) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(
            oldItem: History,
            newItem: History
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: History,
            newItem: History
        ): Boolean {
            return oldItem == newItem
        }
    })

    private var fullList: List<History> = listOf()

    fun submitData(data: List<History>) {
        fullList = data
        dataDiffer.submitList(data)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.bookingCode.contains(query, ignoreCase = true) ||
                        it.airportDepartureCity.contains(query, ignoreCase = true) ||
                        it.airportArrivalCity.contains(query, ignoreCase = true)
            }
        }
        dataDiffer.submitList(filteredList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemHistoryTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class HistoryViewHolder(
        private val binding: ItemHistoryTripBinding,
        val itemClick: (History) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: History) {
            binding.status.text = item.paymentStatus ?: "pending"

            when (item.paymentStatus) {
                "paid" -> {
                    binding.status.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_status_history
                        )

                    binding.status.text = item.paymentStatus
                }


                "" -> {
                    binding.status.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_status_history_pending
                        )
                    binding.status.text = item.paymentStatus
                }


                "expired" -> {
                    binding.status.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_status_history_issue
                        )
                    binding.status.text = item.paymentStatus
                }

                else -> {
                    binding.status.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_status_history_pending
                        )
                    binding.status.text = item.paymentStatus ?: "pending"
                }
            }

            binding.tvCodeBooking.text = item.bookingCode
            binding.tvAirportDeparatureName.text = item.airportDepartureCity
            binding.tvAirportArrivalName.text = item.airportArrivalCity
            binding.tvDateDeparature.text = item.departureTime.toCustomDateFormat()
            binding.tvDateArrival.text = item.arrivalTime.toCustomDateFormat()
            binding.tvTimeDeparature.text = item.departureTime.toCustomTimeFormat()
            binding.tvTimeArrival.text = item.arrivalTime.toCustomTimeFormat()
            binding.tvClassType.text = item.flightClass
            binding.tvPrice.text = item.price.toIDRFormat()


            val estimatedTime = item.departureTime.toCustomTimeFormat()?.let { departureTime ->
                item.arrivalTime.toCustomTimeFormat()?.let { arrivalTime ->
                    calculateEstimatedTime(departureTime, arrivalTime)
                }
            }
            binding.tvEstimatedTime.text = estimatedTime

            itemView.setOnClickListener { itemClick(item) }
        }

    }
}
