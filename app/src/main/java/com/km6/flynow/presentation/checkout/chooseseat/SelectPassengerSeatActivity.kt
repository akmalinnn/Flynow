package com.km6.flynow.presentation.checkout.chooseseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.R
import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.model.Flight
import com.km6.flynow.databinding.ActivitySelectPassengerSeatBinding
import com.km6.flynow.data.model.Passenger
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.SeatPayloads
import com.km6.flynow.data.source.network.model.history.Seat
import com.km6.flynow.data.source.network.model.seat.SeatData
import com.km6.flynow.presentation.checkout.checkout_penumpang.PassengerItem
import com.km6.flynow.utils.proceedWhen
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener
import dev.jahidhasanco.seatbookview.SeatLongClickListener
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
    private lateinit var passengers: ArrayList<Passenger>


    private var searchParams: Search? = null
    private var booking: Booking? = null

    private var title =
        listOf(
            "/", "A1", "B1", "C1", "", "D1", "E1", "F1",
            "/", "A2", "B2", "C2", "", "D2", "E2", "F2",
            "/", "A3", "B3", "C3", "", "D3", "E3", "F3",
            "/", "A4", "B4", "C4", "", "D4", "E4", "F4",
            "/", "A5", "B5", "C5", "", "D5", "E5", "F5",
            "/", "A6", "B6", "C6", "", "D6", "E6", "F6",
            "/", "A7", "B7", "C7", "", "D7", "E7", "F7",
            "/", "A8", "B8", "C8", "", "D8", "E8", "F8",
            "/", "A9", "B9", "C9", "", "D9", "E9", "F9",
            "/", "A10", "B10", "C10", "", "D10", "E10", "F10",
            "/", "A11", "B11", "C11", "", "D11", "E11", "F11",
            "/", "A12", "B12", "C12", "", "D12", "E12", "F12",
            "/", "A13", "B13", "C13", "", "D13", "E13", "F13",
            "/", "A14", "B14", "C14", "", "D14", "E14", "F14",
        )

    private val arrTitle = title.filter { it.isNotEmpty() && !it.contains("/") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        searchParams = intent.getParcelableExtra("SEARCH_PARAMS")
        booking = intent.getParcelableExtra("BOOKING")
        passengers = intent.getParcelableArrayListExtra("PASSENGER_LIST") ?: arrayListOf()

        getSeat()



        setClickListener()
        Log.d("Passenger Data List", "onCreate: ${passengers}}")
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
    //                    showAlertDialog(it.exception?.message.orEmpty())
                    },
                )
            }
        }
    }


    private fun setSeatView(seats: List<SeatData>) {
        val newSeats = generateSeatsString(viewModel.capacity, seats)
        seatBookView = findViewById(R.id.layoutSeat)
        seatBookView.setSeatsLayoutString(newSeats)
            .isCustomTitle(true)
            .setCustomTitle(title)
            .setSeatLayoutPadding(2)
            .setSelectSeatLimit(searchParams?.totalPassenger!!)
            .setSeatSizeBySeatsColumnAndLayoutWidth(7, -1)

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

    private fun generateSeatsString(
        capacity: Int,
        seats: List<SeatData>,
    ): String {
        Log.d("Seat", "generateSeatsString: $seats")
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
        }
    }


    private fun setSelectedSeat() {
        val selectedSeatIds = seatBookView.getSelectedIdList()
        val selectedSeatNumbersFromApi = mutableListOf<String>()

        viewModel.seats.value?.let { seats ->
            selectedSeatIds.forEach { id ->
                val seat = seats.getOrNull(id - 1)
                seat?.let {
                    selectedSeatNumbersFromApi.add(it.seatCode)
                }

            }
        }

        val seatPayloads = SeatPayloads(selectedSeatNumbersFromApi, null)
        booking?.seatPayloads?.departureSeats = seatPayloads.departureSeats
    }




    companion object {
//        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
//        const val EXTRA_BOOKING= "EXTRA_FLIGHT"

        fun startActivity(
            context: Context,
//            params: Search,
//            booking : Booking,
        ) {
            val intent = Intent(context, SelectPassengerSeatActivity::class.java)
//            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
//            intent.putExtra(EXTRA_BOOKING, booking)
//            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
//            intent.putExtra(EXTRA_PASSENGER_DATA, passengerData)
            context.startActivity(intent)
        }
    }

}

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        // Retrieve extras from intent
//        searchParams = intent.getParcelableExtra("SEARCH_PARAMS") ?: Search()
//        passengers = intent.getParcelableArrayListExtra("PASSENGER_LIST") ?: ArrayList()
//
//        seatBookView = findViewById(R.id.layoutSeat)
//        seatBookView.setSeatsLayoutString(seats)
//            .isCustomTitle(true)
//            .setCustomTitle(title)
//            .setSeatLayoutPadding(2)
//            .setSeatSizeBySeatsColumnAndLayoutWidth(7, -1)
//        // ParentLayoutWeight -1 if Your seatBookView layout_width = match_parent / wrap_content
//
//        seatBookView.show()
//
//        seatBookView.setSeatClickListener(
//            object : SeatClickListener {
//                override fun onAvailableSeatClick(
//                    selectedIdList: List<Int>,
//                    view: View,
//                ) {
//                    // Handle seat click events
//                }
//
//                override fun onBookedSeatClick(view: View) {
//                    // Handle booked seat click events
//                }
//
//                override fun onReservedSeatClick(view: View) {
//                    // Handle reserved seat click events
//                }
//            },
//        )
//
//        seatBookView.setSeatLongClickListener(
//            object : SeatLongClickListener {
//                override fun onAvailableSeatLongClick(view: View) {
//                    // Handle long click on available seats
//                    Toast.makeText(this@SelectPassengerSeatActivity, "Long Pressed", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onBookedSeatLongClick(view: View) {
//                    // Handle long click on booked seats
//                }
//
//                override fun onReservedSeatLongClick(view: View) {
//                    // Handle long click on reserved seats
//                }
//            },
//        )
//
//        // Add a button click listener to save and show selected seats
//        binding.btnSave.setOnClickListener {
//            val selectedSeats =
//                seatBookView.getSelectedIdList().joinToString(", ") { id ->
//                    arrTitle[id - 1]
//                }
//            Toast.makeText(this, "Selected Seats: $selectedSeats", Toast.LENGTH_SHORT).show()
//        }
//    }


