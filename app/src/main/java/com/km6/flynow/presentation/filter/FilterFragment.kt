package com.km6.flynow.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.km6.flynow.data.model.Filter
import com.km6.flynow.databinding.FragmentFilterBinding
import com.km6.flynow.presentation.choose_seat_class.ChooseSeatClassFragment
import com.km6.flynow.presentation.filter.adapter.FilterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterBinding

    private val viewModel: FilterViewModel by viewModel()

    private var filterSelectionListener: OnFilterSelectedListener? = null

    private lateinit var adapter: FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFilterBinding.inflate(inflater, container, false)
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

        binding.btnFilterButton.setOnClickListener {
            val selectedPosition = viewModel.selectedPosition.value
            if (selectedPosition != null && selectedPosition >= 0) {
                val selectedFilter = viewModel.options.value?.get(selectedPosition)
                if (selectedFilter != null) {
                    filterSelectionListener?.onFilterSelected(
                        selectedFilter,
                        selectedPosition
                    )
                    dismiss()
                }
            }
        }
    }

    private fun observeData() {
        viewModel.options.observe(viewLifecycleOwner, Observer { options ->
            adapter = FilterAdapter(options, viewModel, viewLifecycleOwner)
            binding.rvListFilter.adapter = adapter
        })
    }

    fun setOnFilterSelectedListener(listener: OnFilterSelectedListener) {
        filterSelectionListener = listener
    }

    companion object {
        private const val ARG_SELECTED_POSITION = "selected_position"

        @JvmStatic
        fun newInstance(selectedPosition: Int) =
            FilterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SELECTED_POSITION, selectedPosition)
                }
            }
    }
}

interface OnFilterSelectedListener {
    fun onFilterSelected(filter: Filter, position: Int)
}