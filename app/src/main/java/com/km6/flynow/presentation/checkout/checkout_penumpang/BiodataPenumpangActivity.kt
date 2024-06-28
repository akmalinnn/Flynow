package com.km6.flynow.presentation.checkout.checkout_penumpang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Search
import com.km6.flynow.databinding.ActivityBiodataPenumpangBinding
import com.km6.flynow.presentation.checkout.chooseseat.ChooseSeatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class BiodataPenumpangActivity : AppCompatActivity() {
    private val binding: ActivityBiodataPenumpangBinding by lazy {
        ActivityBiodataPenumpangBinding.inflate(layoutInflater)
    }

    private var searchParams: Search? = null

    private val bioAdapter = GroupAdapter<GroupieViewHolder>()
    private val items = mutableListOf<PassengerItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setClick()

        searchParams = intent.getParcelableExtra("SEARCH_PARAMS")
//
//        val numAdult = intent.getIntExtra("numAdult", 0)
//        val numChild = intent.getIntExtra("numChild", 0)
//        val numBaby = intent.getIntExtra("numBaby", 0)

        addPassengerForms(searchParams?.adult ?: 0 , searchParams?.child ?:0, searchParams?.baby ?:0)
//        addPassengerForms(numAdult, numChild, numBaby)

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
//                val intent = ChooseSeatActivity.Intent(this, ArrayList(passengerList))
                startActivity(intent)
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