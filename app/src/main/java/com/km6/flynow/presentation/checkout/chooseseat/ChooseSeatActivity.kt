package com.km6.flynow.presentation.checkout.chooseseat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.R
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.Seat
import com.km6.flynow.databinding.ActivityChooseSeatBinding
import com.km6.flynow.presentation.checkout.chooseseat.returnflight.ReturnChooseSeatActivity
import com.km6.flynow.presentation.checkout.chooseseat.seatbookview.SeatBookView
import com.km6.flynow.presentation.checkout.chooseseat.seatbookview.SeatClickListener
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChooseSeatActivity : AppCompatActivity() {
    private val binding: ActivityChooseSeatBinding by lazy {
        ActivityChooseSeatBinding.inflate(layoutInflater)
    }
    private val viewModel: ChooseSeatViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private lateinit var seatBookView: SeatBookView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.flight?.id?.let { getSeat(it) }
        setTitle()
        setClickListener()
        Log.d("Passenger Data List", "onCreate: ${viewModel.passengerDataList}")
    }

    private fun getSeat(id : Int) {
        viewModel.getSeat(id).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { seat ->
                        setSeatView(seat)
                        viewModel.seat.value = seat
                    }
                },
            )
        }
    }

    private fun setTitle() {
        binding.tvTitleInfoSeat.text =
            getString(
                R.string.binding_title_seat,
                viewModel.search?.clas?.name,
                viewModel.capacity.toString(),
            )
    }

    private fun setSeatView(seats: List<Seat>) {
        val newSeats = generateSeatsString(viewModel.capacity, seats)
        val seatCode = seats.map { it.seatCode }
        Log.d("title", "setSeatView: $seatCode")
        val title = generateSeatTitles(seatCode)
        seatBookView = findViewById(R.id.layout_chooseSeat)
        seatBookView.setSeatsLayoutString(newSeats)
            .isCustomTitle(true)
            .setCustomTitle(title)
            .setSeatLayoutPadding(2)
            .setSelectSeatLimit(viewModel.selectableSeat)
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
        seats: List<Seat>,
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
                    sb.append("_") // Fill remaining spaces
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

    private fun generateSeatTitles(seatNumbers: List<String>): List<String> {
        val titles = mutableListOf<String>()
        val rowCount = seatNumbers.size / 6 + if (seatNumbers.size % 6 != 0) 1 else 0

        for (i in 0 until rowCount) {
            titles.add("/") // Start of a new row
            for (j in 0..2) {
                val seatIndex = i * 6 + j
                if (seatIndex < seatNumbers.size) {
                    titles.add(seatNumbers[seatIndex])
                }
            }
            titles.add("") // Separator
            for (j in 3..5) {
                val seatIndex = i * 6 + j
                if (seatIndex < seatNumbers.size) {
                    titles.add(seatNumbers[seatIndex])
                }
            }
        }

        return titles
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            setSelectedSeat()
        }
    }

    private fun setSelectedSeat() {
        val selectedSeatIds = seatBookView.getSelectedIdList()
        val selectedSeatCodeFromApi = mutableListOf<String>()

        viewModel.seats.value?.let { seats ->
            selectedSeatIds.forEach { id ->
                val seat = seats.getOrNull(id - 1)
                seat?.let {
                    selectedSeatCodeFromApi.add(it.seatCode)
                }
                Toast.makeText(this, "Selected Seats: $selectedSeatIds", Toast.LENGTH_SHORT).show()
                if (viewModel.search?.roundTrip == true){
                    navigateToCheckout(
                        viewModel.totalPrice!!,
                        viewModel.flight!!,
                        viewModel.flightReturn,
                        viewModel.search!!,
                        viewModel.passengerDataList!!,
                        seats,
                    )
                } else if (viewModel.search?.roundTrip == false) {
                    navigateToSeatReturn(
                        viewModel.totalPrice!!,
                        viewModel.flight!!,
                        viewModel.flightReturn,
                        viewModel.search!!,
                        viewModel.passengerDataList!!,
                        seats,
                    )
                }
            }
        }
    }

    private fun navigateToSeatReturn(
        totalPrice: Double,
        flight: Flight,
        flightReturn: Flight?,
        search: Search,
        passengerList: List<BioPenumpang>,
        seats: List<Seat>,
    ) {
        ReturnChooseSeatActivity.startActivity(
            this,
            search,
            flight,
            flightReturn,
            totalPrice,
            passengerList,
            seats,
        )
    }

    private fun navigateToCheckout(
        totalPrice: Double,
        flight: Flight,
        flightReturn: Flight?,
        search: Search,
        passengerList: List<BioPenumpang>,
        seats: List<Seat>,
    ) {
//        CheckoutDetailActivity.startActivity(
//            this,
//            totalPrice,
//            flight,
//            flightReturn,
//            search,
//            passengerList,
//            seats,
//            null,
//        )
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_FLIGHT_RETURN = "EXTRA_FLIGHT_RETURN"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"
        const val EXTRA_PASSENGER_DATA = "EXTRA_PASSENGER_DATA"

        fun startActivity(
            context: Context,
            search: Search,
            flight: Flight,
            flightReturn: Flight?,
            totalPrice: Double,
            passengerData: List<BioPenumpang>,
        ) {
            val intent = Intent(context, ChooseSeatActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, search)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_FLIGHT_RETURN, flightReturn)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            intent.putParcelableArrayListExtra(EXTRA_PASSENGER_DATA, ArrayList(passengerData))
            context.startActivity(intent)
        }
    }
}


//    private val binding: ActivityChooseSeatBinding by lazy {
//        ActivityChooseSeatBinding.inflate(layoutInflater)
//    }
//
//    private lateinit var seatBookView: SeatBookView
//    private var seats = (
//            "/AAA_AAA" +
//                    "/UAA_ARA" +
//                    "/AAA_AAA" +
//                    "/RUA_AAA" +
//                    "/AAA_ARA" +
//                    "/AUA_AAA" +
//                    "/AAA_AAA" +
//                    "/AAA_AAA" +
//                    "/RUA_AAA" +
//                    "/AAA_ARA" +
//                    "/AUA_AAA" +
//                    "/AAA_AAA" +
//                    "/AAA_AAA" +
//                    "/AAA_AAA"
//
//            )
//
//    private var title =
//        listOf(
//            "/", "A1", "B1", "C1", "", "D1", "E1", "F1",
//            "/", "A2", "B2", "C2", "", "D2", "E2", "F2",
//            "/", "A3", "B3", "C3", "", "D3", "E3", "F3",
//            "/", "A4", "B4", "C4", "", "D4", "E4", "F4",
//            "/", "A5", "B5", "C5", "", "D5", "E5", "F5",
//            "/", "A6", "B6", "C6", "", "D6", "E6", "F6",
//            "/", "A7", "B7", "C7", "", "D7", "E7", "F7",
//            "/", "A8", "B8", "C8", "", "D8", "E8", "F8",
//            "/", "A9", "B9", "C9", "", "D9", "E9", "F9",
//            "/", "A10", "B10", "C10", "", "D10", "E10", "F10",
//            "/", "A11", "B11", "C11", "", "D11", "E11", "F11",
//            "/", "A12", "B12", "C12", "", "D12", "E12", "F12",
//            "/", "A13", "B13", "C13", "", "D13", "E13", "F13",
//            "/", "A14", "B14", "C14", "", "D14", "E14", "F14",
//        )
//
//    private val arrTitle = title.filter { it.isNotEmpty() && !it.contains("/") }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        seatBookView = findViewById(R.id.nsv_seat_book_view)
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
//                }
//
//                override fun onBookedSeatClick(view: View) {
//                }
//
//                override fun onReservedSeatClick(view: View) {
//                }
//            },
//        )
//
//        seatBookView.setSeatLongClickListener(
//            object : SeatLongClickListener {
//                override fun onAvailableSeatLongClick(view: View) {
//                    Toast.makeText(this@ChooseSeatActivity, "Long Pressed", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onBookedSeatLongClick(view: View) {
//                }
//
//                override fun onReservedSeatLongClick(view: View) {
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
//}