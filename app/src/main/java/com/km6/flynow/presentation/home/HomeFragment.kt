package com.km6.flynow.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.km6.flynow.data.model.Airport
import com.km6.flynow.databinding.FragmentHomeBinding
import com.km6.flynow.presentation.choose_destination.ChooseDestinationFragment
import com.km6.flynow.presentation.choose_destination.DestinationSelectionListener
import com.km6.flynow.presentation.choose_passanger.ChoosePassangerFragment

class HomeFragment : Fragment(), DestinationSelectionListener {
    private lateinit var binding: FragmentHomeBinding

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

    }

    private fun setClickAction() {
        chooseDestination()
        choosePassanger()
    }

    private fun choosePassanger() {
        binding.layoutSearch.tvPassengerLabel.setOnClickListener{
            val dialog = ChoosePassangerFragment()
            dialog.show(parentFragmentManager, dialog.tag)
        }
        binding.layoutSearch.tvPassengerValue.setOnClickListener{
            val dialog = ChoosePassangerFragment()
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
            binding.layoutSearch.tvCityFrom.text = airport.city
        } else if (source == "to") {
            binding.layoutSearch.tvCityTo.text = airport.city
        }
    }
}