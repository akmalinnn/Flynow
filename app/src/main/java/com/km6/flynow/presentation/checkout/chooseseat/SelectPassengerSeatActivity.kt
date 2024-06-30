package com.km6.flynow.presentation.checkout.chooseseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.km6.flynow.R
import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.model.Flight
import com.km6.flynow.databinding.ActivitySelectPassengerSeatBinding
import com.km6.flynow.data.model.Passenger
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.SeatPayloads
import com.km6.flynow.data.source.network.model.history.Seat
import com.km6.flynow.data.source.network.model.seat.SeatData
import com.km6.flynow.presentation.checkout.checkout_detail.CheckoutDetailActivity
import com.km6.flynow.presentation.checkout.checkout_penumpang.PassengerItem
import com.km6.flynow.utils.proceedWhen
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener
import dev.jahidhasanco.seatbookview.SeatLongClickListener
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectPassengerSeatActivity : AppCompatActivity() {
    private val binding: ActivitySelectPassengerSeatBinding by lazy {
        ActivitySelectPassengerSeatBinding.inflate(layoutInflater)
    }

    private val viewModel: SelectPassengerSeatViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private lateinit var seatBookView: SeatBookView
    private var seatBookViewReturn: SeatBookView? = null
    private lateinit var passengers: ArrayList<Passenger>

    private var searchParams: Search? = null
    private var booking: Booking? = null

    private var title =
        listOf(
            "/", "1A", "1B", "1C", "", "1D", "1E", "1F",
            "/", "2A", "2B", "2C", "", "2D", "2E", "2F",
            "/", "3A", "3B", "3C", "", "3D", "3E", "3F",
            "/", "4A", "4B", "4C", "", "4D", "4E", "4F",
            "/", "5A", "5B", "5C", "", "5D", "5E", "5F",
            "/", "6A", "6B", "6C", "", "6D", "6E", "6F",
            "/", "7A", "7B", "7C", "", "7D", "7E", "7F",
            "/", "8A", "8B", "8C", "", "8D", "8E", "8F",
            "/", "9A", "9B", "9C", "", "9D", "9E", "9F",
            "/", "10A", "10B", "10C", "", "10D", "10E", "10F",
            "/", "11A", "11B", "11C", "", "11D", "11E", "11F",
            "/", "12A", "12B", "12C", "", "12D", "12E", "12F",
            "/", "13A", "13B", "13C", "", "13D", "13E", "13F",
            "/", "14A", "14B", "14C", "", "14D", "14E", "14F"
        )

    private val arrTitle = title.filter { it.isNotEmpty() && !it.contains("/") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        searchParams = intent.getParcelableExtra("SEARCH_PARAMS")
        booking = intent.getParcelableExtra("BOOKING")
        passengers = intent.getParcelableArrayListExtra("PASSENGER_LIST") ?: arrayListOf()

        getSeat()
        getSeatReturn()
        setClickListener()
    }

    private fun getSeat() {
        booking?.departureFlightId?.let {
            viewModel.getSeat(it).observe(this) { it ->
                it.proceedWhen(
                    doOnSuccess = {
                        it.payload?.let { seat ->
                            setSeatView(seat)
                            viewModel.seat.value = seat
                        }
                    },
                    doOnError = {

                    },

                    doOnEmpty = {

                    }
                )
            }
        }
    }

    private fun getSeatReturn() {
        booking?.returnFlightId?.let {
            viewModel.getSeat(it).observe(this) { it ->
                it.proceedWhen(
                    doOnSuccess = {
                        binding.tvTitleInfoSeatReturn.visibility = View.VISIBLE
                        it.payload?.let { seat ->
                            setSeatViewReturn(seat)
                            viewModel.seat.value = seat
                        }
                    },
                    doOnError = {

                    },

                    doOnEmpty = {
                    binding.layoutChooseSeatReturn.visibility = View.GONE
                    booking?.seatPayloads?.returnSeats = null
                        booking?.returnFlightId = null

                    binding.tvTitleInfoSeatReturn.visibility = View.GONE
                    }

                )
            }
        }
    }


    private fun setSeatView(seats: List<SeatData>) {
        val newSeats = generateSeatsString(viewModel.capacity, seats)
        seatBookView = findViewById(R.id.layout_chooseSeat)
        seatBookView.setSeatsLayoutString(newSeats)
            .isCustomTitle(true)
            .setCustomTitle(title)
            .setSeatLayoutPadding(2)
            .setSelectSeatLimit(searchParams?.totalPassenger!!)
            .setSeatSizeBySeatsColumnAndLayoutWidth(8, -1)

        seatBookView.show()

        seatBookView.setSeatClickListener(
            object : SeatClickListener {
                override fun onAvailableSeatClick(
                    selectedIdList: List<Int>,
                    view: View,
                ) {
                }

                override fun onBookedSeatClick(view: View) {
                }

                override fun onReservedSeatClick(view: View) {
                }
            },
        )
    }


    private fun setSeatViewReturn(seats: List<SeatData>) {
        val newSeats = generateSeatsStringReturn(viewModel.capacity, seats)
        seatBookViewReturn = findViewById(R.id.layout_chooseSeat_return)
        seatBookViewReturn?.setSeatsLayoutString(newSeats)
            ?.isCustomTitle(true)
            ?.setCustomTitle(title)
            ?.setSeatLayoutPadding(2)
            ?.setSelectSeatLimit(searchParams?.totalPassenger!!)
            ?.setSeatSizeBySeatsColumnAndLayoutWidth(8, -1)

        seatBookViewReturn?.show()

        seatBookViewReturn?.setSeatClickListener(
            object : SeatClickListener {
                override fun onAvailableSeatClick(
                    selectedIdList: List<Int>,
                    view: View,
                ) {
                }

                override fun onBookedSeatClick(view: View) {
                }

                override fun onReservedSeatClick(view: View) {
                }
            },
        )
    }


    private fun generateSeatsString(
        capacity: Int,
        seats: List<SeatData>,
    ): String {
        val sb = StringBuilder()
        var remainingCapacity = capacity
        var seatCounter = 0
        var space = 3
        sb.append("/")

        while (remainingCapacity > 0) {
            if (seatCounter % 6 == 0 && seatCounter > 0) {
                sb.append("/")
            }

            val seatsInRow = minOf(remainingCapacity, 3)
            repeat(seatsInRow) {
                val currentSeatIndex = seatCounter + it
                if (currentSeatIndex < seats.size) {
                    val seat = seats[currentSeatIndex]
                    sb.append(if (seat.seatAvailable) "A" else "U") // Use API data
                } else {
                    sb.append("_")
                }
            }

            remainingCapacity -= seatsInRow
            seatCounter += seatsInRow
            space += seatsInRow

            if (space % 6 == 0 && remainingCapacity > 0) {
                sb.append("_")
            }
        }

        return sb.toString()
    }


    private fun generateSeatsStringReturn(
        capacity: Int,
        seats: List<SeatData>,
    ): String {
        val sb = StringBuilder()
        var remainingCapacity = capacity
        var seatCounter = 0
        var space = 3
        sb.append("/")

        while (remainingCapacity > 0) {
            if (seatCounter % 6 == 0 && seatCounter > 0) {
                sb.append("/")
            }

            val seatsInRow = minOf(remainingCapacity, 3)
            repeat(seatsInRow) {
                val currentSeatIndex = seatCounter + it
                if (currentSeatIndex < seats.size) {
                    val seat = seats[currentSeatIndex]
                    sb.append(if (seat.seatAvailable) "A" else "U") // Use API data
                } else {
                    sb.append("_")
                }
            }

            remainingCapacity -= seatsInRow
            seatCounter += seatsInRow
            space += seatsInRow

            if (space % 6 == 0 && remainingCapacity > 0) {
                sb.append("_")
            }
        }

        return sb.toString()
    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener {
            setSelectedSeat()
            setSelectedSeatReturn()

            val totalSelectedSeats = (booking?.seatPayloads?.departureSeats?.size ?: 0)

            val totalSelectedSeatsReturn = (booking?.seatPayloads?.returnSeats?.size ?: 0)

            if ( (totalSelectedSeats != searchParams?.totalPassenger) && (totalSelectedSeatsReturn != searchParams?.totalPassenger)) {
                Toast.makeText(this, "Please select seats for all passengers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                }

            else {

            booking?.let { booking ->
                viewModel.viewModelScope.launch {
                    viewModel.createBooking(booking)
                }
                }
            }
            val intent = Intent(this, CheckoutDetailActivity::class.java).apply {
                putExtra("BOOKING", booking)
                putExtra("SEARCH_PARAMS", searchParams)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
        }
    }




    private fun setSelectedSeat() {
        val selectedSeatIds = seatBookView.getSelectedIdList()
        val selectedSeatCode = mutableListOf<String>()
        viewModel.seats.value?.let { seats ->
            selectedSeatIds.forEach { id ->
                val seat = seats.getOrNull(id - 1)
                seat?.let {
                    selectedSeatCode.add(it.seatCode)
                }
            }
        }
        booking?.seatPayloads?.departureSeats = selectedSeatCode
    }

    private fun setSelectedSeatReturn() {
        val selectedSeatIds = seatBookViewReturn?.getSelectedIdList()
        val selectedSeatCodeReturn = mutableListOf<String>()
        viewModel.seats.value?.let { seats ->
            selectedSeatIds?.forEach { id ->
                val seat = seats.getOrNull(id - 1)
                seat?.let {
                    selectedSeatCodeReturn.add(it.seatCode)
                }
            }
        }
        booking?.seatPayloads?.returnSeats = selectedSeatCodeReturn
    }




    companion object {

        fun startActivity(
            context: Context,
        ) {
            val intent = Intent(context, SelectPassengerSeatActivity::class.java)
            context.startActivity(intent)
        }
    }

}



