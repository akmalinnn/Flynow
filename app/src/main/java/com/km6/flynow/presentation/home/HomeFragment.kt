    package com.km6.flynow.presentation.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.view.isVisible
import com.km6.flynow.R
import com.km6.flynow.data.model.Airport
import com.km6.flynow.data.model.FavoriteFlight
import com.km6.flynow.data.model.Search
import com.km6.flynow.data.model.SeatClass
import com.km6.flynow.data.model.history.History
import com.km6.flynow.databinding.FragmentHomeBinding
import com.km6.flynow.presentation.calendar.CalendarBottomSheetFragment
import com.km6.flynow.presentation.calendar.OnDateSelectedListener
import com.km6.flynow.presentation.choose_destination.ChooseDestinationFragment
import com.km6.flynow.presentation.choose_destination.DestinationSelectionListener
import com.km6.flynow.presentation.choose_passanger.ChoosePassangerFragment
import com.km6.flynow.presentation.choose_passanger.ChoosePassangerViewModel
import com.km6.flynow.presentation.choose_passanger.PassengerCountDataListener
import com.km6.flynow.presentation.choose_seat_class.ChooseSeatClassFragment
import com.km6.flynow.presentation.choose_seat_class.OnSeatClassSelectedListener
import com.km6.flynow.presentation.filter_result.FilterResultActivity
import com.km6.flynow.presentation.history.HistoryAdapter
import com.km6.flynow.presentation.history.historydetail.HistoryDetailActivity
import com.km6.flynow.presentation.home.adapter.FavoriteListAdapter
import com.km6.flynow.presentation.home.favorite_flight.FavoriteDetailActivity
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.utils.GridSpacingItemDecoration
import com.km6.flynow.utils.getFormattedDate
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

class HomeFragment : Fragment(), DestinationSelectionListener, OnDateSelectedListener,
    PassengerCountDataListener,
    OnSeatClassSelectedListener {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()

    private val productAdapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter {
            navigateToDetailActivity(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickAction()
        setupView()
        setupListFlightFavorite()
        getHistoryList()
    }


    private fun setupView() {
        if (viewModel.airportFrom == null) {
            binding.layoutSearch.tvCityFrom.text = "Pilih Asal"
        } else {
            val airportFromText = getString(
                R.string.city_from,
                viewModel.airportFrom!!.city,
                viewModel.airportFrom!!.airportCode
            )
            binding.layoutSearch.tvCityFrom.text = airportFromText
            binding.layoutSearch.tvCityFrom.setTextColor(Color.BLACK)
        }

        if (viewModel.airportTo == null) {
            binding.layoutSearch.tvCityTo.text = "Pilih Destinasi"
        } else {
            val airportToText = getString(
                R.string.city_to,
                viewModel.airportTo!!.city,
                viewModel.airportTo!!.airportCode
            )
            binding.layoutSearch.tvCityTo.text = airportToText
            binding.layoutSearch.tvCityTo.setTextColor(Color.BLACK)
        }

        if (viewModel.totalPassenger == 0) {
            binding.layoutSearch.tvPassengerValue.text = "Pilih Penumpang"
        } else {
            binding.layoutSearch.tvPassengerValue.text =
                getString(R.string.passenger_value, viewModel.totalPassenger.toString())
            binding.layoutSearch.tvPassengerValue.setTextColor(Color.BLACK)
        }

        if (viewModel.departureDate == 0L) {
            binding.layoutSearch.tvDepatureValue.text = "Pilih Tanggal"
        } else {
            binding.layoutSearch.tvDepatureValue.text = viewModel.departureDate.getFormattedDate()
            binding.layoutSearch.tvDepatureValue.setTextColor(Color.BLACK)
        }

        if (viewModel.returnDate == 0L) {
            binding.layoutSearch.tvReturnValue.text = "Pilih Tanggal"
        } else {
            binding.layoutSearch.tvReturnValue.text = viewModel.returnDate.getFormattedDate()
            binding.layoutSearch.tvReturnValue.setTextColor(Color.BLACK)
        }

        if (viewModel.seatClass?.name == null) {
            binding.layoutSearch.tvSeatValue.text = "Pilih Kelas"
        } else {
            binding.layoutSearch.tvSeatValue.text = viewModel.seatClass!!.name
            binding.layoutSearch.tvSeatValue.setTextColor(Color.BLACK)
        }

        switchState(viewModel.isRoundTrip)
    }

    private fun setClickAction() {
        chooseDestination()
        choosePassenger()
        chooseDate()
        chooseSeatClass()
        swapAirport()
        switchListener()
        search()
    }

    private fun switchListener() {
        binding.layoutSearch.switch1.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isRoundTrip = isChecked
            switchState(viewModel.isRoundTrip)
        }
    }

    private fun switchState(isRoundTrip: Boolean) {
        if (isRoundTrip) {
            binding.layoutSearch.sectionReturnDate.isClickable = true
            binding.layoutSearch.tvReturnValue.setTextColor(getResources().getColor(R.color.md_theme_primary))
        } else {
            binding.layoutSearch.sectionReturnDate.isClickable = false
            binding.layoutSearch.tvReturnValue.setTextColor(Color.GRAY)
        }
    }

    private fun search() {

        val searchParameters = Search(
            da = viewModel.airportFrom,
            aa = viewModel.airportTo,
            dd = viewModel.departureDate.toString(),
            rd = viewModel.returnDate.toString(),
            totalPassenger = viewModel.totalPassenger,
            adult = viewModel.adultCount,
            child = viewModel.childrenCount,
            baby = viewModel.babyCount,
            clas = viewModel.seatClass,
            roundTrip = viewModel.roundTrip
        )
        binding.layoutSearch.btnSearchButton.setOnClickListener {
            val intent = Intent(requireContext(), FilterResultActivity::class.java).apply {
                putExtra("SEARCH_PARAMS", searchParameters)
            }
            startActivity(intent)
        }
    }

    private fun chooseSeatClass() {
        binding.layoutSearch.sectionChooseSeatClass.setOnClickListener {
            val selectedPosition = viewModel.seatClassPosition
            val dialog = ChooseSeatClassFragment.newInstance(selectedPosition)
            dialog.setOnSeatClassSelectedListener(this)
            dialog.show(parentFragmentManager, dialog.tag)
        }
    }

    private fun swapAirport() {
        binding.layoutSearch.ivSwitch.setOnClickListener {
            if (viewModel.airportFrom != null && viewModel.airportTo != null) {
                val tempAirport = viewModel.airportFrom
                viewModel.airportFrom = viewModel.airportTo
                viewModel.airportTo = tempAirport

                val airportFromText = getString(
                    R.string.city_from,
                    viewModel.airportFrom?.city,
                    viewModel.airportFrom?.airportCode
                )
                binding.layoutSearch.tvCityFrom.text = airportFromText

                val airportToText = getString(
                    R.string.city_to,
                    viewModel.airportTo?.city,
                    viewModel.airportTo?.airportCode
                )
                binding.layoutSearch.tvCityTo.text = airportToText
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error : Please select destination first",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun chooseDate() {
        binding.layoutSearch.sectionDepartureDate.setOnClickListener {
            val dialog = CalendarBottomSheetFragment.newInstance(
                "departureDate",
                viewModel.departureDate,
                viewModel.departureDate
            )
            dialog.setOnDateSelectedListener(this)
            dialog.show(parentFragmentManager, dialog.tag)
        }

        binding.layoutSearch.sectionReturnDate.setOnClickListener {
            val dialog = CalendarBottomSheetFragment.newInstance(
                "returnDate",
                viewModel.returnDate,
                viewModel.departureDate
            )
            dialog.setOnDateSelectedListener(this)
            dialog.show(parentFragmentManager, dialog.tag)
        }
    }

    private fun choosePassenger() {
        binding.layoutSearch.sectionChoosePassenger.setOnClickListener {
            val dialog = ChoosePassangerFragment.newInstance(
                viewModel.adultCount,
                viewModel.childrenCount,
                viewModel.babyCount,
                viewModel.totalPassenger
            )
            dialog.setPassengerCountDataListener(this)
            dialog.show(parentFragmentManager, dialog.tag)
        }
    }

    private fun chooseDestination() {
        binding.layoutSearch.tvCityFrom.setOnClickListener {
            val dialog = ChooseDestinationFragment.newInstance("from")
            dialog.setDestinationSelectionListener(this)
            dialog.show(parentFragmentManager, dialog.tag)
        }
        binding.layoutSearch.tvCityTo.setOnClickListener {
            val dialog = ChooseDestinationFragment.newInstance("to")
            dialog.setDestinationSelectionListener(this)
            dialog.show(parentFragmentManager, dialog.tag)
        }
    }

    override fun onDestinationSelected(airport: Airport, source: String) {
        if (source == "from") {
            if (airport.airportCode == viewModel.airportTo?.airportCode) {
                Toast.makeText(
                    requireContext(),
                    "Error : Departure airport is the same as Destination com.km6.flynow.data.source.network.model.history.Airport",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.airportFrom = airport
                setupView()
            }
        } else if (source == "to") {
            if (airport.airportCode == viewModel.airportFrom?.airportCode) {
                Toast.makeText(
                    requireContext(),
                    "Error : Destination com.km6.flynow.data.source.network.model.history.Airport is the same as Departure airport",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.airportTo = airport
                setupView()
            }
        }
    }

    override fun onDateSelected(
        selectedDate: Long,
        formattedDate: String,
        isoDate: String,
        source: String
    ) {
        if (source == "departureDate") {
            viewModel.departureDate = selectedDate
            binding.layoutSearch.tvDepatureValue.text = formattedDate
            binding.layoutSearch.tvDepatureValue.setTextColor(Color.BLACK)

        } else if (source == "returnDate") {
            viewModel.returnDate = selectedDate
            binding.layoutSearch.tvReturnValue.text = formattedDate
            binding.layoutSearch.tvReturnValue.setTextColor(Color.BLACK)
        }
    }

    override fun onPassengerDataUpdated(
        adultCount: Int,
        childrenCount: Int,
        babyCount: Int,
        totalPassenger: Int
    ) {
        viewModel.updatePassengerData(adultCount, childrenCount, babyCount, totalPassenger)
        binding.layoutSearch.tvPassengerValue.text =
            getString(R.string.passenger_value, viewModel.totalPassenger.toString())
        setupView()
    }

    override fun onSeatClassSelected(seatClass: SeatClass, position: Int) {
        viewModel.seatClass = seatClass
        viewModel.seatClassPosition = position
        setupView()
    }

    private fun setupListFlightFavorite() {
        val itemDecoration = GridSpacingItemDecoration(1, 10, true)
        binding.rvListDestination.apply {
            adapter = productAdapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun getHistoryList() {

        viewModel.getFavoriteFlight().observe(viewLifecycleOwner) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.shimmerLayout.isVisible = true
                    binding.rvListDestination.isVisible = false

                },
                doOnSuccess = {
                    binding.rvListDestination.isVisible = true
                    it.payload?.let { data -> bindFlightFavoriteList(data) }
                    binding.shimmerLayout.isVisible = false
                },
                doOnError = { error ->
                    Toast.makeText(
                        requireContext(),
                        "Error: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnEmpty = { error ->
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.anda_belum_memiliki_riwayat),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun bindFlightFavoriteList(data: List<FavoriteFlight>) {
        productAdapter.submitData(data)
    }


    private fun navigateToDetailActivity(historyItem: FavoriteFlight) {
        val intent = Intent(requireContext(), FavoriteDetailActivity::class.java).apply {
            putExtra(FavoriteDetailActivity.EXTRA_FAVORITE_FLIGHT_ITEM, historyItem)
        }
        startActivity(intent)
    }
}