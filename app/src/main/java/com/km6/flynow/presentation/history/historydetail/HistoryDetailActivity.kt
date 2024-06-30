package com.km6.flynow.presentation.history.historydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.km6.flynow.R
import com.km6.flynow.data.model.history.History
import com.km6.flynow.databinding.ActivityDetailHistoryBinding
import com.km6.flynow.presentation.main.MainActivity
import com.km6.flynow.presentation.payment.PaymentActivity
import com.km6.flynow.utils.toCustomDateFormat
import com.km6.flynow.utils.toCustomTimeFormat
import com.km6.flynow.utils.toIDRFormat
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
            binding.tvPricePassengersBabies.text = totalPrice.toIDRFormat()
        }

        viewModel.historyItem.observe(this) { historyItem ->
            historyItem?.let { bindHistoryItem(it) } ?: finish()
        }

    }


    private fun backtoMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )

    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
            backtoMain()
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
            val paymentStatus = historyItem.paymentStatus

            when (paymentStatus) {
                "paid" -> {
                    binding.tvIssued.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_status_history
                        )
                    binding.tvIssued.text = historyItem.paymentStatus
                }

                "" -> { binding.tvIssued.background =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.bg_status_history_pending)
                    binding.tvIssued.text = binding.root.context.getString(R.string.pending)
                }
                "expired" -> {
                    binding.tvIssued.background =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_status_history_issue
                        )
                    binding.tvIssued.text = historyItem.paymentStatus
                }
                else -> {
                    tvIssued.background = ContextCompat.getDrawable(root.context, R.drawable.bg_status_history_pending)
                    binding.tvIssued.text = binding.root.context.getString(R.string.pending)
                }

            }

            val destinationText =
                "${historyItem.airportDepartureCity} -> ${historyItem.airportArrivalCity}"
            tvFlightDestination.text = destinationText

            val passengerNames = historyItem.passengerName.joinToString(", ")
            val passengerSeat = historyItem.passengerSeat.joinToString(", ")
            val passengerSeatReturn = historyItem.passengerSeatReturn?.joinToString(", ")

            seatReturn.text = passengerSeatReturn
            seat.text = passengerSeat


            tvNumberBookingCode.text = historyItem.bookingCode
            tvTakeOffTime.text = historyItem.departureTime.toCustomTimeFormat()
            tvTakeOffDate.text = historyItem.departureTime.toCustomDateFormat()
            tvAirportOrigin.text = historyItem.airportDepartureName
            tvFlightCode.text = historyItem.flightCode
            ivLogo.setImageResource(R.drawable.ic_airline)

            val flightClass = "${historyItem.airlineDepartureName} - ${historyItem.flightClass}"
            tvFlightName.text = flightClass
            tvLandingTime.text = historyItem.arrivalTime.toCustomTimeFormat()
            tvLandingDate.text = historyItem.arrivalTime.toCustomDateFormat()
            tvNamePassenger.text = passengerNames
            tvAirportDestination.text = historyItem.airportArrivalName
            ivLogo.load(historyItem.airlineLogo) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }

            tvNamePassengerPayload.text = historyItem.flightInformation
            tvPrice.text = historyItem.price.toIDRFormat()




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
                binding.tvFlightDestinationReturn.visibility = View.GONE
            } else {
                cvSectionCheckoutReturn.visibility = View.VISIBLE
            }

            val adultsText = getString(R.string.adults_label, historyItem.numAdults)
            val childrenText = getString(R.string.children_label, historyItem.numChildren)
            val babiesText = getString(R.string.babies_label, historyItem.numBabies)



            tvPriceDetailsPassengers.text = getString(R.string.adultText, adultsText)
            tvPriceDetailsPassengersChildren.text = getString(R.string.childernText, childrenText)
            tvPriceDetailsPassengersBabies.text = getString(R.string.babiesText, babiesText)
            ivLogoReturn.load(historyItem.airlineLogo) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }


            tvNamePassengerPayloadReturn.text = historyItem.flightInformationReturn
            tvPriceReturn.text = historyItem.priceReturn.toIDRFormat()


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
