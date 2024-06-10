    package com.km6.flynow.presentation.home

    import android.graphics.Color
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import com.km6.flynow.R
    import com.km6.flynow.data.model.Airport
    import com.km6.flynow.databinding.FragmentHomeBinding
    import com.km6.flynow.presentation.calendar.CalendarBottomSheetFragment
    import com.km6.flynow.presentation.calendar.OnDateSelectedListener
    import com.km6.flynow.presentation.choose_destination.ChooseDestinationFragment
    import com.km6.flynow.presentation.choose_destination.DestinationSelectionListener
    import com.km6.flynow.presentation.choose_passanger.ChoosePassangerFragment
    import com.km6.flynow.presentation.choose_passanger.ChoosePassangerViewModel
    import com.km6.flynow.presentation.choose_passanger.PassengerCountDataListener
    import org.koin.androidx.viewmodel.ext.android.viewModel
    import java.util.Date

    class HomeFragment : Fragment(), DestinationSelectionListener, OnDateSelectedListener, PassengerCountDataListener {
        private lateinit var binding: FragmentHomeBinding

        private val viewModel: HomeViewModel by viewModel()

        private var airportFrom: Airport? = null
        private var airportTo: Airport? = null
        private var departureDate: Long = 0L
        private var returnDate: Long = 0L

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
            setupView()
            setClickAction()

        }

        private fun setupView() {
            binding.layoutSearch.tvCityFrom.text = "Pilih Asal"
            binding.layoutSearch.tvCityTo.text = "Pilih Tujuan"
            binding.layoutSearch.tvPassengerValue.text = "Pilih Penumpang"
        }

        private fun setClickAction() {
            chooseDestination()
            choosePassanger()
            chooseDate()
            swapAirport()
        }

        private fun swapAirport() {
            binding.layoutSearch.ivSwitch.setOnClickListener {
                if (airportFrom != null && airportTo != null) {
                    val tempAirport = airportFrom
                    airportFrom = airportTo
                    airportTo = tempAirport

                    val airportFromText = getString(R.string.city_from, airportFrom?.city, airportFrom?.airportCode)
                    binding.layoutSearch.tvCityFrom.text = airportFromText

                    val airportToText = getString(R.string.city_to, airportTo?.city, airportTo?.airportCode)
                    binding.layoutSearch.tvCityTo.text = airportToText
                } else {
                    Toast.makeText(requireContext(), "Error : Please select destination first", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun chooseDate() {
            binding.layoutSearch.ivCalendarDepature.setOnClickListener {
                val dialog = CalendarBottomSheetFragment.newInstance("departureDate", departureDate, departureDate)
                dialog.setOnDateSelectedListener(this)
                dialog.show(parentFragmentManager, dialog.tag)
            }

            binding.layoutSearch.ivCalendarReturn.setOnClickListener {
                val dialog = CalendarBottomSheetFragment.newInstance("returnDate", returnDate, departureDate)
                dialog.setOnDateSelectedListener(this)
                dialog.show(parentFragmentManager, dialog.tag)
            }
        }

        private fun choosePassanger() {
            binding.layoutSearch.tvPassengerLabel.setOnClickListener{
                val dialog = ChoosePassangerFragment.newInstance(viewModel.adultCount,viewModel.childrenCount,viewModel.babyCount,viewModel.totalPassenger)
                dialog.setPassengerCountDataListener(this)
                dialog.show(parentFragmentManager, dialog.tag)
            }
            binding.layoutSearch.tvPassengerValue.setOnClickListener{
                val dialog = ChoosePassangerFragment.newInstance(viewModel.adultCount,viewModel.childrenCount,viewModel.babyCount,viewModel.totalPassenger)
                dialog.setPassengerCountDataListener(this)
                dialog.show(parentFragmentManager, dialog.tag)
            }
        }

        private fun chooseDestination() {
            binding.layoutSearch.tvCityFrom.setOnClickListener{
                val dialog = ChooseDestinationFragment.newInstance("from")
                dialog.setDestinationSelectionListener(this)
                dialog.show(parentFragmentManager, dialog.tag)
            }
            binding.layoutSearch.tvCityTo.setOnClickListener{
                val dialog = ChooseDestinationFragment.newInstance("to")
                dialog.setDestinationSelectionListener(this)
                dialog.show(parentFragmentManager, dialog.tag)
            }
        }

        override fun onDestinationSelected(airport: Airport, source: String) {
            if (source == "from") {
                if (airport.airportCode == airportTo?.airportCode) {
                    Toast.makeText(requireContext(), "Error : Departure airport is the same as Destination Airport", Toast.LENGTH_SHORT).show()
                } else {
                    airportFrom = airport
                    val airportFromText = getString(R.string.city_from, airport.city, airport.airportCode)
                    binding.layoutSearch.tvCityFrom.text = airportFromText
                    binding.layoutSearch.tvCityFrom.setTextColor(Color.BLACK)
                }
            } else if (source == "to") {
                if (airport.airportCode == airportFrom?.airportCode) {
                    Toast.makeText(requireContext(), "Error : Destination Airport is the same as Departure airport", Toast.LENGTH_SHORT).show()
                } else {
                    airportTo = airport
                    val airportToText = getString(R.string.city_to, airport.city, airport.airportCode)
                    binding.layoutSearch.tvCityTo.text = airportToText
                    binding.layoutSearch.tvCityTo.setTextColor(Color.BLACK)
                }
            }
        }

        override fun onDateSelected(selectedDate:Long, formattedDate: String, isoDate:String, source: String) {
            if (source == "departureDate") {
                departureDate = selectedDate
                binding.layoutSearch.tvDepatureValue.text = formattedDate
                binding.layoutSearch.tvDepatureValue.setTextColor(Color.BLACK)

            } else if (source == "returnDate") {
                returnDate = selectedDate
                binding.layoutSearch.tvReturnValue.text = formattedDate
                binding.layoutSearch.tvReturnValue.setTextColor(Color.BLACK)
            }
        }

        override fun onPassengerDataUpdated(adultCount: Int, childrenCount: Int, babyCount: Int, totalPassenger: Int) {
            viewModel.updatePassengerData(adultCount, childrenCount, babyCount, totalPassenger)
            binding.layoutSearch.tvPassengerValue.text = getString(R.string.passenger_value, viewModel.totalPassenger.toString())
        }
    }