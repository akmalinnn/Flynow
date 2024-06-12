package com.km6.flynow.presentation.choose_passanger

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.km6.flynow.R
import com.km6.flynow.databinding.FragmentChooseDestinationBinding
import com.km6.flynow.databinding.FragmentChoosePassangerBinding
import com.km6.flynow.presentation.calendar.CalendarBottomSheetFragment
import com.km6.flynow.presentation.choose_destination.ChooseDestinationFragment
import com.km6.flynow.presentation.choose_destination.ChooseDestinationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChoosePassangerFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChoosePassangerBinding

    private val viewModel: ChoosePassangerViewModel by viewModel {
        parametersOf(arguments)
    }

    private var passengerCountDataListener: PassengerCountDataListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChoosePassangerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setClickListener()
        observeData()

    }

    private fun sendDataToHome() {
        val adultCount = viewModel.adultCountLiveData.value ?: 0
        val childrenCount = viewModel.childrenCountLiveData.value ?: 0
        val babyCount = viewModel.babyCountLiveData.value ?: 0
        val totalPassenger = viewModel.totalCountLiveData.value  ?: 0
        passengerCountDataListener?.onPassengerDataUpdated(adultCount, childrenCount, babyCount, totalPassenger)
    }

    private fun observeData() {
        viewModel.adultCountLiveData.observe(this) {
            binding.layoutCountAdult.tvMenuCount.text = it.toString()
        }
        viewModel.childrenCountLiveData.observe(this) {
            binding.layoutCountChildren.tvMenuCount.text = it.toString()
        }
        viewModel.babyCountLiveData.observe(this) {
            binding.layoutCountBaby.tvMenuCount.text = it.toString()
        }
        viewModel.totalCountLiveData.observe(this) {
            binding.btnSave.isEnabled = it != 0
            binding.tvTotalPassanger.text  = it.toString()
        }
    }

    private fun setClickListener() {
        binding.layoutCountAdult.ivPlus.setOnClickListener {
            viewModel.addAdult()
        }
        binding.layoutCountAdult.ivMinus.setOnClickListener {
            viewModel.minusAdult()
        }
        binding.layoutCountChildren.ivPlus.setOnClickListener {
            viewModel.addChildren()
        }
        binding.layoutCountChildren.ivMinus.setOnClickListener {
            viewModel.minusChildren()
        }
        binding.layoutCountBaby.ivPlus.setOnClickListener {
            viewModel.addBaby()
        }
        binding.layoutCountBaby.ivMinus.setOnClickListener {
            viewModel.minusBaby()
        }
    }

    private fun setupViews() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            sendDataToHome()
            dismiss()
        }
    }
    fun setPassengerCountDataListener(listener: PassengerCountDataListener) {
        this.passengerCountDataListener = listener
    }

    companion object {
        const val EXTRA_ADULT = "adult"
        const val EXTRA_CHILDREN = "children"
        const val EXTRA_BABY = "baby"
        const val EXTRA_TOTAL = "total"

        @JvmStatic
        fun newInstance(adult: Int, children: Int, baby: Int, total: Int) =
            ChoosePassangerFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ADULT, adult)
                    putInt(EXTRA_CHILDREN, children)
                    putInt(EXTRA_BABY, baby)
                    putInt(EXTRA_TOTAL, total)
                }
            }
    }
}

interface PassengerCountDataListener {
    fun onPassengerDataUpdated(adultCount: Int, childrenCount: Int, babyCount: Int, totalPassenger: Int)
}