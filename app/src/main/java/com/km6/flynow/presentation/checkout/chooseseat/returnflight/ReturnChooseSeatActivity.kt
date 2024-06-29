package com.km6.flynow.presentation.checkout.chooseseat.returnflight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.R
import com.km6.flynow.data.model.BioPenumpang
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.Seat
import com.km6.flynow.databinding.ActivityChooseSeatBinding
import com.km6.flynow.presentation.checkout.checkout_detail.CheckoutDetailActivity
import com.km6.flynow.presentation.checkout.chooseseat.seatbookview.SeatBookView
import com.km6.flynow.presentation.checkout.chooseseat.seatbookview.SeatClickListener
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ReturnChooseSeatActivity : AppCompatActivity() {
    private val binding: ActivityChooseSeatBinding by lazy {
        ActivityChooseSeatBinding.inflate(layoutInflater)
    }
    private val viewModel: ReturnChooseSeatViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private lateinit var seatBookView: SeatBookView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getSeat()
        setTitle()
        setClickListener()
        Log.d("Passenger Data List", "onCreate: ${viewModel.passengerDataList}")
    }

    private fun getSeat() {
        viewModel.flightReturn?.id?.let {
            viewModel.getSeat(it).observe(this) { it ->
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
                // Toast.makeText(this, "Selected Seats: $selectedSeatIds", Toast.LENGTH_SHORT).show()
                navigateToCheckout(
                    viewModel.totalPrice!!,
                    viewModel.flight!!,
                    viewModel.flightReturn,
                    viewModel.search!!,
                    viewModel.passengerDataList!!,
                    viewModel.seatDeparture!!,
                    seats,
                )
            }
        }
    }

    private fun navigateToCheckout(
        totalPrice: Double,
        flight: Flight,
        flightReturn: Flight?,
        search: Search,
        passengerList: List<BioPenumpang>,
        seatDeparture: List<Seat>,
        seats: List<Seat>,
    ) {
        CheckoutDetailActivity.startActivity(
            this,
            totalPrice,
            flight,
            flightReturn,
            search,
            passengerList,
            seatDeparture,
            seats,
        )
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_FLIGHT_RETURN = "EXTRA_FLIGHT_RETURN"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"
        const val EXTRA_PASSENGER_DATA = "EXTRA_PASSENGER_DATA"
        const val EXTRA_SEAT = "EXTRA_SEAT"

        fun startActivity(
            context: Context,
            search: Search,
            flight: Flight,
            flightReturn: Flight?,
            totalPrice: Double,
            passengerData: List<BioPenumpang>,
            seats: List<Seat>,
        ) {
            val intent = Intent(context, ReturnChooseSeatActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, search)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_FLIGHT_RETURN, flightReturn)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            intent.putExtra(EXTRA_PASSENGER_DATA, arrayListOf(passengerData))
            intent.putExtra(EXTRA_SEAT, arrayListOf(seats))
            context.startActivity(intent)
        }
    }
}
