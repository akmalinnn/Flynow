package com.km6.flynow.presentation.choose_seat_class

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.km6.flynow.R
import com.km6.flynow.data.model.SeatClass
import com.km6.flynow.databinding.FragmentChooseDestinationBinding
import com.km6.flynow.databinding.FragmentChooseSeatClassBinding
import com.km6.flynow.presentation.choose_destination.ChooseDestinationFragment
import com.km6.flynow.presentation.choose_destination.ChooseDestinationViewModel
import com.km6.flynow.presentation.choose_destination.DestinationSelectionListener
import com.km6.flynow.presentation.choose_seat_class.adapter.SeatClassAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseSeatClassFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChooseSeatClassBinding

    private val viewModel: ChooseSeatClassViewModel by viewModel()

    private var seatClassSelectionListener: OnSeatClassSelectedListener? = null

    private lateinit var adapter: SeatClassAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChooseSeatClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setClickListener()

        // Restore previously selected position if available
        arguments?.getInt(ARG_SELECTED_POSITION)?.let { position ->
            viewModel.selectPosition(position)
        }
    }

    private fun setClickListener() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val selectedPosition = viewModel.selectedPosition.value
            if (selectedPosition != null && selectedPosition >= 0) {
                val selectedSeatClass = viewModel.options.value?.get(selectedPosition)
                if (selectedSeatClass != null) {
                    seatClassSelectionListener?.onSeatClassSelected(selectedSeatClass, selectedPosition)
                    dismiss()
                }
            }
        }
    }

    private fun observeData() {
        viewModel.options.observe(viewLifecycleOwner, Observer { options ->
            adapter = SeatClassAdapter(options, viewModel, viewLifecycleOwner)
            binding.rvSeatClass.adapter = adapter
        })
    }

    fun setOnSeatClassSelectedListener(listener: OnSeatClassSelectedListener) {
        seatClassSelectionListener = listener
    }

    companion object {
        private const val ARG_SELECTED_POSITION = "selected_position"

        @JvmStatic
        fun newInstance(selectedPosition: Int) =
            ChooseSeatClassFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SELECTED_POSITION, selectedPosition)
                }
            }
    }
}

interface OnSeatClassSelectedListener {
    fun onSeatClassSelected(seatClass: SeatClass, position: Int)
}