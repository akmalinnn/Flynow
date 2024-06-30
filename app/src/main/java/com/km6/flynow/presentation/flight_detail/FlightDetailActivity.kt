package com.km6.flynow.presentation.flight_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.km6.flynow.R
import com.km6.flynow.data.model.Booking
import com.km6.flynow.databinding.ActivityFlightDetailBinding
import com.km6.flynow.presentation.checkout.checkout_pemesan.BiodataPemesanActivity
import com.km6.flynow.presentation.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.SeatPayloads
import com.km6.flynow.data.source.network.model.flight.DepartureFlight
import com.km6.flynow.data.source.network.model.flight.ReturnFlight
import com.km6.flynow.presentation.checkout.chooseseat.SelectPassengerSeatActivity
import com.km6.flynow.presentation.main.MainActivity
import com.km6.flynow.presentation.profile.ProfileViewModel
import com.km6.flynow.utils.getFormatDate
import com.km6.flynow.utils.getFormattedDate
import com.km6.flynow.utils.getTime
import com.km6.flynow.utils.proceedWhen
import com.km6.flynow.utils.toDateFormat
import com.km6.flynow.utils.toIDRFormat
import com.km6.flynow.utils.toTimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel


class FlightDetailActivity : AppCompatActivity() {
    private val binding: ActivityFlightDetailBinding by lazy {
        ActivityFlightDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: FlightDetailViewModel by viewModel()
    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var departureFlight: Flight
    private var returnFlight: Flight? = null
    private var roundTrip: Boolean = false
    private var searchParams: Search? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        departureFlight = intent.getParcelableExtra("DEPARTURE_FLIGHT")!!

        Log.d("departureFlight", "onCreate: $departureFlight")
        returnFlight = intent.getParcelableExtra("RETURN_FLIGHT")
        Log.d("returnFlight", "onCreate: $returnFlight")
        roundTrip = intent.getBooleanExtra("ROUND_TRIP", false)

        searchParams = intent.getParcelableExtra("SEARCH_PARAMS")


        // Display flight details
        displayFlightDetails()

        setClickListener()
    }




    private fun setClickListener() {

        val token = viewModel.getToken()
        if (token != null) {
            binding.btnCheckout.setOnClickListener {
                navigateToBiodataPemesan()
            }
        }
        else {
            binding.btnCheckout.setOnClickListener {
                navigateToLogin()
            }
        }


        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
        }

    }


    private fun navigateToLogin() {
        profileViewModel.logOut()
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }


    private fun setBindReturn(returnFlight: Flight?) {
        Log.d("returnFlight", "setBindReturn: $returnFlight")
        binding.layoutArrivalFlight.tvDestination.text = getString(
            R.string.depature_destination,
            returnFlight?.depaturecity,
            returnFlight?.arrivalcity
        )
        binding.layoutArrivalFlight.itemFlightDetail.apply {
            tvDepartureTime.text = returnFlight?.departureTime.toTimeFormat()
            tvDepartureDate.text = returnFlight?.departureTime.toDateFormat()
            tvAirportDeparture.text = getString(
                R.string.terminal,
                returnFlight?.depatureairportName,
                returnFlight?.terminal
            )
            tvAirlineDeparture.text = returnFlight?.airlineName
            tvSeatClass.text = returnFlight?.flightClass
            tvNumberPlane.text = returnFlight?.flightCode
            tvDetailInformation.text = returnFlight?.information
            tvReturnTime.text = returnFlight?.arrivalTime.toTimeFormat()
            tvReturnDate.text = returnFlight?.arrivalTime.toDateFormat()
            tvAirportReturn.text = returnFlight?.arrivalairportName

            ivAirlineDeparture.load(returnFlight?.image) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }

        }
    }

    private fun setBindDepature(departureFlight: Flight) {
        Log.d("departureFlight", "setBindDepature: $departureFlight")
        binding.layoutDepartureFlight.tvDestination.text = getString(
            R.string.depature_destination,
            departureFlight.depaturecity,
            departureFlight.arrivalcity
        )
        val formattedTime = departureFlight.departureTime.toTimeFormat()
        Log.d("formattedTime", "Formatted Departure Time: $formattedTime")
        binding.layoutDepartureFlight.itemFlightDetail.tvDepartureTime.text = formattedTime
        Log.d(
            "tvDepartureTime",
            "Departure Time TextView: ${binding.layoutDepartureFlight.itemFlightDetail.tvDepartureTime.text}"
        )
        binding.layoutDepartureFlight.itemFlightDetail.apply {

            tvDepartureDate.text = departureFlight.departureTime.toDateFormat()
            tvAirportDeparture.text = getString(
                R.string.terminal,
                departureFlight.depatureairportName,
                departureFlight.terminal
            )
            tvAirlineDeparture.text = departureFlight.airlineName
            tvSeatClass.text = departureFlight.flightClass
            tvNumberPlane.text = departureFlight.flightCode
            tvDetailInformation.text = departureFlight.information
            tvReturnTime.text = departureFlight.arrivalTime.toTimeFormat()
            tvReturnDate.text = departureFlight.arrivalTime.toDateFormat()
            tvAirportReturn.text = departureFlight.arrivalairportName

            ivAirlineDeparture.load(departureFlight.image) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }


        }
    }

    private fun navigateToBiodataPemesan() {
        val booking = searchParams?.let {
            Booking(
                departureFlightId = departureFlight.id,
                returnFlightId = returnFlight?.id,
                numAdults = it.adult,
                numChildren = it.child,
                numBabies = it.baby,
                passengerPayloads = emptyList(),
                seatPayloads = SeatPayloads(
                    emptyList(),
                    emptyList()
                )
            )
        }

        startActivity(
            Intent(this, BiodataPemesanActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("SEARCH_PARAMS", searchParams)
                putExtra("DEPARTURE_FLIGHT", departureFlight)
                putExtra("RETURN_FLIGHT", returnFlight)
                putExtra("BOOKING", booking)
            }
        )
    }

    private fun displayFlightDetails() {
        Log.d("departureFlight", "displayFlightDetails: $departureFlight")
        if (roundTrip) {
            // Round trip, display both flights
            Log.d("returnFlight", "displayFlightDetails: $returnFlight")
//            getFlightDetail(departureFlight.id)
//            getFlightDetail(returnFlight?.id)
            setBindDepature(departureFlight)
            setBindReturn(returnFlight)
        } else {
            // One-way trip, display departure flight only
            setBindDepature(departureFlight)
        }

        binding.apply {
            // Tambahkan field lainnya sesuai kebutuhan

            if (roundTrip) {
                tvArrival.visibility = View.VISIBLE
                layoutArrivalFlight.root.visibility = View.VISIBLE
                layoutArrivalFlight.tvDestination.text =
                    getString(
                        R.string.depature_destination,
                        returnFlight?.depaturecity,
                        returnFlight?.arrivalcity
                    )
                layoutArrivalFlight.itemFlightDetail.tvDepartureTime.text =
                    returnFlight?.departureTime
                // Tambahkan field lainnya sesuai kebutuhan
            } else {
                layoutArrivalFlight.root.visibility = View.GONE
                tvArrival.visibility = View.GONE
            }
        }
    }

}