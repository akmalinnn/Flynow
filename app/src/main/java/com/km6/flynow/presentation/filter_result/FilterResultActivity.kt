package com.km6.flynow.presentation.filter_result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.data.model.Filter
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.databinding.ActivityFilterResultBinding
import com.km6.flynow.presentation.filter.FilterFragment
import com.km6.flynow.presentation.filter.OnFilterSelectedListener
import com.km6.flynow.presentation.filter_result.adapter.FilterResultAdapter
import com.km6.flynow.presentation.flight_detail.FlightDetailActivity
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

//class FilterResultActivity : AppCompatActivity() {
//    private val binding: ActivityFilterResultBinding by lazy {
//        ActivityFilterResultBinding.inflate(layoutInflater)
//    }
//    private val viewModel: FilterResultViewModel by viewModel()
//    private val filterResultAdapter: FilterResultAdapter by lazy {
//        FilterResultAdapter {
//            getSearchFlight(it.depatureairportName,it.arrivalairportName,it.departureTime,it.arrivalTime,it.flightClass)
//            navigateToDetail(it)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        setupListFlight()
//        getSearchFlight("CGK", "DPS", "2024-08-02", "2024-08-02","1","1","0","economy","price-asc")
//
//    }
//
//    private fun getSearchFlight(da: String? = null,
//                                aa: String? = null,
//                                dd: String? = null,
//                                rd: String? = null,
//                                adult: String? = null,
//                                child: String? = null,
//                                baby: String? = null,
//                                clas: String? = null,
//                                sort: String? = null) {
//        viewModel.searchFlight(da, aa, dd, rd, adult, child, baby, clas, sort).observe(this){
//            it.proceedWhen(
//                doOnSuccess = {
//                    val flightResponse = it.payload?.first
//                    val returnFlightResponse = it.payload?.second
//                    bindFlight(flightResponse)
//                    Log.d("flightResponse", "getSearchFlight: $flightResponse")
//                    Log.d("returnflightResponse", "getSearchFlight: $returnFlightResponse")
//                },
//                doOnError = {
//                    Log.d("error", "getSearchFlight: ${it.exception?.message}")
//                }
//            )
//        }
//
//    }
//
//    private fun setupListFlight() {
//        binding.rvListFlight.apply {
//            adapter = filterResultAdapter
//            layoutManager =
//                LinearLayoutManager(this@FilterResultActivity, LinearLayoutManager.VERTICAL, false)
//        }
//    }
//
//
//    private fun bindFlight(data: List<Flight>?) {
//        filterResultAdapter.submitData((data))
//    }
//
//    private fun navigateToDetail(it: Flight) {
//        startActivity(Intent(this, FlightDetailActivity::class.java))
//    }
//}


class FilterResultActivity : AppCompatActivity(), OnFilterSelectedListener {
    private val binding: ActivityFilterResultBinding by lazy {
        ActivityFilterResultBinding.inflate(layoutInflater)
    }
    private val viewModel: FilterResultViewModel by viewModel()
    private val filterResultAdapter: FilterResultAdapter by lazy {
        FilterResultAdapter {
            handleFlightSelection(it)
        }
    }

    private var selectedDepartureFlight: Flight? = null
    private var searchParams: Search? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // ini diganti jadi ngambil data class yg baru contoh bentuknya
        // ini di bawah buat ngambil data di detail, di sini nanti tinggal ganti tag nya yang sesuai sama halaman home
//        departureFlight = intent.getParcelableExtra("DEPARTURE_FLIGHT")!!
//        returnFlight = intent.getParcelableExtra("RETURN_FLIGHT")

        searchParams = intent.getParcelableExtra("SEARCH_PARAMS")


        setupListFlight()
        getSearchFlight(searchParams?.da?.airportCode, searchParams?.aa?.airportCode, searchParams?.dd, searchParams?.rd, searchParams?.adult.toString(), searchParams?.child.toString(), searchParams?.baby.toString(), searchParams?.clas.toString(), "price-asc")
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnFilterButton.setOnClickListener{
            chooseFilter()
        }
    }

    private fun chooseFilter() {
        val selectedPosition = viewModel.filterPosition
        val dialog = FilterFragment.newInstance(selectedPosition)
        dialog.setOnFilterSelectedListener(this)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun getSearchFlight(
        da: String? = null,
        aa: String? = null,
        dd: String? = null,
        rd: String? = null,
        adult: String? = null,
        child: String? = null,
        baby: String? = null,
        clas: String? = null,
        sort: String? = null
    ) {
        viewModel.searchFlight(da, aa, dd, rd, adult, child, baby, clas, sort).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    val flightResponse = it.payload?.first
                    val returnFlightResponse = it.payload?.second
                    bindFlight(if (selectedDepartureFlight == null || !searchParams?.roundTrip!!) flightResponse else returnFlightResponse)
                    Log.d("flightResponse", "getSearchFlight: $flightResponse")
                    Log.d("returnflightResponse", "getSearchFlight: $returnFlightResponse")
                },
                doOnError = {
                    Log.d("error", "getSearchFlight: ${it.exception?.message}")
                }
            )
        }
    }

    private fun setupListFlight() {
        binding.rvListFlight.apply {
            adapter = filterResultAdapter
            layoutManager = LinearLayoutManager(this@FilterResultActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun bindFlight(data: List<Flight>?) {
        filterResultAdapter.submitData(data)
    }

    private fun handleFlightSelection(flight: Flight) {
        if (!searchParams?.roundTrip!!) {
            // One-way trip, navigate to detail directly
            navigateToDetail(flight, null)
        } else {
            if (selectedDepartureFlight == null) {
                // Stage 1: Departure flight selected, store it and load return flights
                selectedDepartureFlight = flight
                getSearchFlight(flight.arrivalairportName, flight.depatureairportName, flight.arrivalTime, flight.arrivalTime, "1", "1", "0", "economy", "price-asc")
            } else {
                // Stage 2: Return flight selected, navigate to detail with both flights
                navigateToDetail(selectedDepartureFlight!!, flight)
            }
        }
    }

    private fun navigateToDetail(departureFlight: Flight, returnFlight: Flight?) {
        val intent = Intent(this, FlightDetailActivity::class.java).apply {
            putExtra("DEPARTURE_FLIGHT", departureFlight)
            putExtra("RETURN_FLIGHT", returnFlight)
        }
        startActivity(intent)
    }

    override fun onFilterSelected(filter: Filter, position: Int) {
        viewModel.selectedFilter = filter
        viewModel.filterPosition = position
        binding.btnFilterSelectedButton.text = filter.desc
    }

}
