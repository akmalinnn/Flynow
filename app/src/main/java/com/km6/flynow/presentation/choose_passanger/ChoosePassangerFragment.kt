package com.km6.flynow.presentation.choose_passanger

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.km6.flynow.R
import com.km6.flynow.databinding.FragmentChooseDestinationBinding
import com.km6.flynow.databinding.FragmentChoosePassangerBinding
import com.km6.flynow.presentation.choose_destination.ChooseDestinationFragment
import com.km6.flynow.presentation.choose_destination.ChooseDestinationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChoosePassangerFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChoosePassangerBinding

    private val viewModel: ChoosePassangerViewModel by viewModel()

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

    }

    private fun setupViews() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }
}