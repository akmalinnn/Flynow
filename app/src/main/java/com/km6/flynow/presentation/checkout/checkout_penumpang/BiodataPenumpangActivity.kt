package com.km6.flynow.presentation.checkout.checkout_penumpang

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.databinding.ActivityBiodataPenumpangBinding
import com.km6.flynow.presentation.checkout.chooseseat.ChooseSeatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BiodataPenumpangActivity : AppCompatActivity() {
    private val binding: ActivityBiodataPenumpangBinding by lazy {
        ActivityBiodataPenumpangBinding.inflate(layoutInflater)
    }

    private val viewModel: BiodataPenumpangViewModel by viewModel{
        parametersOf(intent.extras)
    }
    private val bioAdapter = GroupAdapter<GroupieViewHolder>()
    private val items = mutableListOf<PassengerItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setClick()
        addPassengerForms(viewModel.search?.adult ?: 0 , viewModel.search?.child ?:0, viewModel.search?.baby ?:0)
    }

    private fun addPassengerForms(numAdult: Int, numChild: Int, numBaby: Int) {
        for (i in 0 until numAdult) {
            items.add(PassengerItem(BioPenumpang(type = "Adult")))
        }
        for (i in 0 until numChild) {
            items.add(PassengerItem(BioPenumpang(type = "Child")))
        }
        for (i in 0 until numBaby) {
            items.add(PassengerItem(BioPenumpang(type = "Baby")))
        }
        bioAdapter.addAll(items)
    }

    private fun collectPassengerData(): List<BioPenumpang>? {
        val passengerList = mutableListOf<BioPenumpang>()
        items.forEach {
            if (!it.validateForm()) {
                return null
            }
            passengerList.add(it.getPassengerData())
        }
        return passengerList
    }

    private fun setClick() {
        binding.btnToChooseSeat.setOnClickListener {
            val passengerList = collectPassengerData()
            if (passengerList != null) {
              navToChooseSeat(
                  viewModel.search!!,
                  viewModel.flight!!,
                  viewModel.flightReturn,
                  viewModel.totalPrice!!,
                  collectPassengerData()!!
              )
            }
        }
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navToChooseSeat(
        search: Search,
        flight: Flight,
        flightReturn: Flight?,
        totalPrice: Double,
        passengerData: List<BioPenumpang>,
    ) {
        ChooseSeatActivity.startActivity(
            this,
            search,
            flight,
            flightReturn,
            totalPrice,
            passengerData
        )
    }

    private fun setRecyclerView() {
        binding.rvFormPassenger.apply {
            layoutManager = LinearLayoutManager(this@BiodataPenumpangActivity)
            adapter = bioAdapter
        }
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_FLIGHT_RETURN = "EXTRA_FLIGHT_RETURN"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"

        fun startActivity(
            context: Context,
            search: Search,
            flight: Flight,
            flightReturn: Flight?,
            totalPrice: Double
        ) {
            val intent = Intent(context, ChooseSeatActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, search)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_FLIGHT_RETURN, flightReturn)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            context.startActivity(intent)
        }
    }

}