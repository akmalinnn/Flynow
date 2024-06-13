package com.km6.flynow.presentation.filter_result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.data.model.Flight
import com.km6.flynow.databinding.ActivityFilterResultBinding
import com.km6.flynow.presentation.filter_result.adapter.FilterResultAdapter
import com.km6.flynow.presentation.flight_detail.FlightDetailActivity
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterResultActivity : AppCompatActivity() {
    private val binding: ActivityFilterResultBinding by lazy {
        ActivityFilterResultBinding.inflate(layoutInflater)
    }
    private val viewModel: FilterResultViewModel by viewModel()
    private val filterResultAdapter: FilterResultAdapter by lazy {
        FilterResultAdapter {
            navigateToDetail(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListFlight()
        getFlightData()

    }

    private fun setupListFlight() {
        binding.rvListFlight.apply {
            adapter = filterResultAdapter
            layoutManager =
                LinearLayoutManager(this@FilterResultActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getFlightData() {
        viewModel.getFlights().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindFlight(data) }
                }
            )
        }
    }

    private fun bindFlight(data: List<Flight>) {
        filterResultAdapter.submitData((data))
    }

    private fun navigateToDetail(it: Flight) {
        startActivity(Intent(this, FlightDetailActivity::class.java))
    }
}