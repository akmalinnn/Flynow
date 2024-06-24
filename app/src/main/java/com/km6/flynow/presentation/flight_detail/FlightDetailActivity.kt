package com.km6.flynow.presentation.flight_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.R
import com.km6.flynow.data.model.Flight
import com.km6.flynow.databinding.ActivityFlightDetailBinding

class FlightDetailActivity : AppCompatActivity() {
    private val binding: ActivityFlightDetailBinding by lazy {
        ActivityFlightDetailBinding.inflate(layoutInflater)
    }

    private lateinit var departureFlight: Flight
    private var returnFlight: Flight? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_detail)

        departureFlight = intent.getParcelableExtra("DEPARTURE_FLIGHT")!!
        returnFlight = intent.getParcelableExtra("RETURN_FLIGHT")

        // Display flight details
        displayFlightDetails()
    }

    private fun displayFlightDetails() {
        var departureFlightFrom = departureFlight.depaturecity
        var departureFlightTo = departureFlight.arrivalcity
        var returnFlightFrom = returnFlight?.depaturecity
        var returnFlightTo = returnFlight?.arrivalcity
        binding.apply {
            layoutDepartureFlight.tvDestination.text =
                getString(R.string.depature_destination, departureFlightFrom, departureFlightTo)
            layoutDepartureFlight.itemFlightDetail.tvDepartureTime.text = departureFlight.departureTime
        }
    }
}