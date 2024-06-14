package com.km6.flynow.presentation.history.historydetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.data.model.HistoryItem
import com.km6.flynow.databinding.ActivityDetailHistoryBinding

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyItem: HistoryItem? = intent.getParcelableExtra(EXTRA_HISTORY_ITEM)

        historyItem?.let { item ->
            bindHistoryItem(item)
        } ?: finish()
    }

    private fun bindHistoryItem(historyItem: HistoryItem) {
        with(binding) {

            val destinationText = "${historyItem.departureCity} -> ${historyItem.arrivalCity}"
            tvDestination.text = destinationText
            tvTotalPrice.text = historyItem.price
            tvTimeSpent.text = historyItem.durationHours
            itemFlightDetail.tvAirportDeparture.text = historyItem.departureCity
            itemFlightDetail.tvDepartureDate.text = historyItem.departureDate
            itemFlightDetail.tvDepartureTime.text = historyItem.departureTime
            itemFlightDetail.tvSeatClass.text = historyItem.flightClass
            itemFlightDetail.tvAirportReturn.text = historyItem.arrivalCity
            itemFlightDetail.tvReturnDate.text = historyItem.arrivalDate
            itemFlightDetail.tvReturnTime.text = historyItem.arrivalCity

        }
    }

    companion object {
        const val EXTRA_HISTORY_ITEM = "extra_history_item"
    }
}
