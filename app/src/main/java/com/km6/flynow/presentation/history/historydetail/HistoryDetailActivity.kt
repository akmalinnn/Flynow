package com.km6.flynow.presentation.history.historydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.km6.flynow.R
import com.km6.flynow.data.model.history.History
import com.km6.flynow.databinding.ActivityDetailHistoryBinding
import com.km6.flynow.presentation.payment.PaymentActivity
import com.km6.flynow.presentation.payment.PaymentViewModel
import com.km6.flynow.utils.ResultWrapper
import com.km6.flynow.utils.proceedWhen
import com.km6.flynow.utils.toCustomDateFormat
import com.km6.flynow.utils.toCustomTimeFormat
import com.km6.flynow.utils.toIDRFormat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    private val viewModel: HistoryDetailViewModel by viewModel {
        parametersOf(intent.extras)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        setClickListener()
    }

    private fun observeViewModel() {
        viewModel.totalPrice.observe(this) { totalPrice ->
            binding.tvTotalPrice.text = totalPrice.toIDRFormat()
        }

        viewModel.totalAdultPrice.observe(this) { totalPrice ->
            binding.tvPricePassengers.text = totalPrice.toIDRFormat()
        }


        viewModel.totalChildrenPrice.observe(this) { totalPrice ->
            binding.tvPricePassengersChildren.text = totalPrice.toIDRFormat()
        }

        viewModel.totalBabyPrice.observe(this) { totalPrice ->
            binding.tvPricePassengersChildren.text = totalPrice.toIDRFormat()
        }

        viewModel.historyItem.observe(this) { historyItem ->
            historyItem?.let { bindHistoryItem(it) } ?: finish()
        }

    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnContinueToBeranda.setOnClickListener {
            val historyItem = viewModel.historyItem.value
            val totalPrice = viewModel.totalPrice.value ?: 0
            if (historyItem != null) {
                if (historyItem.snapUrl.isNullOrEmpty()) {
                startPaymentActivity(historyItem.id, totalPrice)
                } else {
                    binding.nestedScrollView.visibility = View.GONE
                    binding.llActionBar.visibility = View.GONE
                    binding.tvFlightDestinationReturn.visibility = View.GONE
                    binding.cvTotalFlightDetails.visibility = View.GONE
                    loadPaymentWebView(historyItem.snapUrl)
                }
            }
        }
    }



    private fun bindHistoryItem(historyItem: History) {
        with(binding) {
            val destinationText =
                "${historyItem.airportDepartureCity} -> ${historyItem.airportArrivalCity}"
            tvFlightDestination.text = destinationText

            val passengerNames = historyItem.passengerName.joinToString(", ")

            tvIssued.text = historyItem.paymentStatus
            tvNumberBookingCode.text = historyItem.bookingCode
            tvTakeOffTime.text = historyItem.departureTime.toCustomTimeFormat()
            tvTakeOffDate.text = historyItem.departureTime.toCustomDateFormat()
            tvAirportOrigin.text = historyItem.airportDepartureName
            tvFlightCode.text = historyItem.flightCode

            val flightClass = "${historyItem.airlineDepartureName} - ${historyItem.flightClass}"
            tvFlightName.text = flightClass
            tvLandingTime.text = historyItem.arrivalTime.toCustomTimeFormat()
            tvLandingDate.text = historyItem.arrivalTime.toCustomDateFormat()
            tvNamePassenger.text = passengerNames
            tvAirportDestination.text = historyItem.airportArrivalName

            // Return flight details
            val destinationTextReturn =
                "${historyItem.airportArrivalCity} -> ${historyItem.airportDepartureCity}"
            tvFlightDestinationReturn.text = destinationTextReturn
            tvNumberBookingCodeReturn.text = historyItem.bookingCode
            tvTakeOffTimeReturn.text = historyItem.departureTimeReturn?.toCustomTimeFormat()
            tvTakeOffDateReturn.text = historyItem.departureTimeReturn?.toCustomDateFormat()
            tvAirportOriginReturn.text = historyItem.airportDepartureName

            val flightClassReturn =
                "${historyItem.airlineDepartureName} - ${historyItem.flightClass}"
            tvFlightNameReturn.text = flightClassReturn
            tvLandingTimeReturn.text = historyItem.arrivalTimeReturn?.toCustomTimeFormat()
            tvLandingDateReturn.text = historyItem.arrivalTimeReturn?.toCustomDateFormat()
            tvNamePassengerReturn.text = passengerNames
            tvAirportDestinationReturn.text = historyItem.airportArrivalNameReturn
            tvFlightCodeReturn.text = historyItem.flightCodeReturn

            if (historyItem.returnFlightId.isNullOrEmpty()) {
                cvSectionCheckoutReturn.visibility = View.GONE
                tvFlightNameReturn.visibility = View.GONE
            } else {
                cvSectionCheckoutReturn.visibility = View.VISIBLE
            }

            val adultsText = getString(R.string.adults_label, historyItem.numAdults)
            val childrenText = getString(R.string.children_label, historyItem.numChildren)


            tvPriceDetailsPassengers.text = getString(R.string.adultText, adultsText)
            tvPriceDetailsPassengersChildren.text = getString(R.string.childernText, childrenText)
        }
    }

    private fun startPaymentActivity(bookingId: Int, paymentAmount: Int) {
        PaymentActivity.startActivity(this, bookingId, paymentAmount)
    }

    private fun loadPaymentWebView(transactionToken: String) {
        val snapUrl = "$transactionToken"
        binding.webview.apply {
            visibility = View.VISIBLE
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(snapUrl)
        }
    }
    companion object {
        const val EXTRA_HISTORY_ITEM = "extra_history_item"

        fun startActivity(context: Context, item: History) {
            val intent = Intent(context, HistoryDetailActivity::class.java)
            intent.putExtra(EXTRA_HISTORY_ITEM, item)
            context.startActivity(intent)
        }
    }
}
