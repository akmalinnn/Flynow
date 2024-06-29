package com.km6.flynow.presentation.checkout.checkout_penumpang

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.model.Passenger
import com.km6.flynow.data.model.Search
import com.km6.flynow.databinding.ActivityBiodataPenumpangBinding
import com.km6.flynow.presentation.checkout.chooseseat.SelectPassengerSeatActivity
import com.km6.flynow.presentation.home.HomeViewModel
import com.km6.flynow.presentation.login.LoginActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class BiodataPenumpangActivity : AppCompatActivity() {
    private val binding: ActivityBiodataPenumpangBinding by lazy {
        ActivityBiodataPenumpangBinding.inflate(layoutInflater)
    }

    private var searchParams: Search? = null
    private val bioAdapter = GroupAdapter<GroupieViewHolder>()
    private val items = mutableListOf<PassengerItem>()
    private var booking: Booking? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setClick()

        searchParams = intent.getParcelableExtra("SEARCH_PARAMS")
        booking = intent.getParcelableExtra("BOOKING")

        addPassengerForms(
            searchParams?.adult ?: 0,
            searchParams?.child ?: 0,
            searchParams?.baby ?: 0
        )
    }

    private fun addPassengerForms(numAdult: Int, numChild: Int, numBaby: Int) {
        for (i in 0 until numAdult) {
            items.add(PassengerItem(Passenger(passengerType = "Adult")))
        }
        for (i in 0 until numChild) {
            items.add(PassengerItem(Passenger(passengerType = "Child")))
        }
        for (i in 0 until numBaby) {
            items.add(PassengerItem(Passenger(passengerType = "Baby")))
        }
        bioAdapter.addAll(items)
    }

    private fun collectPassengerData(): List<Passenger>? {
        val passengerList = mutableListOf<Passenger>()
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
                booking = booking?.copy(passengerPayloads = passengerList)
                val intent = Intent(this, SelectPassengerSeatActivity::class.java).apply {
                    putExtra("BOOKING", booking)
                    putExtra("SEARCH_PARAMS", searchParams)
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@BiodataPenumpangActivity,
                    "Please fill all required fields for each passenger",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setRecyclerView() {
        binding.rvFormPassenger.apply {
            layoutManager = LinearLayoutManager(this@BiodataPenumpangActivity)
            adapter = bioAdapter
        }
    }
}
