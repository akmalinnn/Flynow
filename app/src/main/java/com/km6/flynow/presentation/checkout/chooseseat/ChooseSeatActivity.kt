package com.km6.flynow.presentation.checkout.chooseseat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.R
import com.km6.flynow.databinding.ActivityChooseSeatBinding
import com.km6.flynow.presentation.checkout.chooseseat.seatbookview.SeatBookView
import com.km6.flynow.presentation.checkout.chooseseat.seatbookview.SeatClickListener
import com.km6.flynow.presentation.checkout.chooseseat.seatbookview.SeatLongClickListener

class ChooseSeatActivity : AppCompatActivity() {
    private val binding: ActivityChooseSeatBinding by lazy {
        ActivityChooseSeatBinding.inflate(layoutInflater)
    }

    private lateinit var seatBookView: SeatBookView
    private var seats = (
            "/AAA_AAA" +
                    "/UAA_ARA" +
                    "/AAA_AAA" +
                    "/RUA_AAA" +
                    "/AAA_ARA" +
                    "/AUA_AAA" +
                    "/AAA_AAA" +
                    "/AAA_AAA" +
                    "/RUA_AAA" +
                    "/AAA_ARA" +
                    "/AUA_AAA" +
                    "/AAA_AAA" +
                    "/AAA_AAA" +
                    "/AAA_AAA"

            )

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
        seatBookView = findViewById(R.id.nsv_seat_book_view)
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(true)
            .setCustomTitle(title)
            .setSeatLayoutPadding(2)
            .setSeatSizeBySeatsColumnAndLayoutWidth(7, -1)
        // ParentLayoutWeight -1 if Your seatBookView layout_width = match_parent / wrap_content

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

        seatBookView.setSeatLongClickListener(
            object : SeatLongClickListener {
                override fun onAvailableSeatLongClick(view: View) {
                    Toast.makeText(this@ChooseSeatActivity, "Long Pressed", Toast.LENGTH_SHORT).show()
                }

                override fun onBookedSeatLongClick(view: View) {
                }

                override fun onReservedSeatLongClick(view: View) {
                }
            },
        )

        // Add a button click listener to save and show selected seats
        binding.btnSave.setOnClickListener {
            val selectedSeats =
                seatBookView.getSelectedIdList().joinToString(", ") { id ->
                    arrTitle[id - 1]
                }
            Toast.makeText(this, "Selected Seats: $selectedSeats", Toast.LENGTH_SHORT).show()
        }
    }
}