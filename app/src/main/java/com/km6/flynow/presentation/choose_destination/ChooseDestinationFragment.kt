package com.km6.flynow.presentation.choose_destination

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.km6.flynow.data.model.Airport
import com.km6.flynow.data.model.DestinationHistory
import com.km6.flynow.databinding.FragmentChooseDestinationBinding
import com.km6.flynow.presentation.choose_destination.adapter.AirportListAdapter
import com.km6.flynow.presentation.choose_destination.adapter.DestinationHistoryAdapter
import com.km6.flynow.presentation.choose_destination.adapter.DestinationHistoryListener
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChooseDestinationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChooseDestinationBinding

    private val viewModel: ChooseDestinationViewModel by viewModel()

    private var destinationSelectionListener: DestinationSelectionListener? = null

    private val destinationHistoryAdapter: DestinationHistoryAdapter by lazy {
        DestinationHistoryAdapter(
            object : DestinationHistoryListener {
                override fun onRemoveDestinationClicked(destinationHistory: DestinationHistory) {
                    viewModel.removeDestination(destinationHistory)
                }
            },
        )
    }

    private var source: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChooseDestinationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
        setupListAirport()
        setupSearchView()
        setOnClickAction()
    }

    private fun setOnClickAction() {
        binding.tvDelete.setOnClickListener {
            viewModel.deleteAllDestinationHistory()
            observeData()
        }
    }

    private fun setupViews() {
        source = arguments?.getString(ARG_SOURCE)

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setupListAirport() {
        val airportAdapter = AirportListAdapter { airport ->
            destinationSelectionListener?.onDestinationSelected(airport, source ?: "")
            dismiss()
        }
        binding.rvAirport.adapter = airportAdapter

        binding.rvListDestination.adapter = destinationHistoryAdapter
    }

    private fun observeData() {
        viewModel.getAllDestinationHistory().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.tvRecentSearch.isVisible = true
                    binding.tvDelete.isVisible = true
                    binding.rvAirport.isVisible = false
                    binding.rvListDestination.isVisible = true
                    result.payload?.let {
                        // set list cart data
                        destinationHistoryAdapter.submitData(it)
                    }
                },
                doOnError = {

                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvListDestination.isVisible = false
                    binding.rvAirport.isVisible = false
                    binding.tvRecentSearch.isVisible = true
                    binding.rvListDestination.isVisible = false
                    binding.tvDelete.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = "Riwayat Kosong"
                    binding.rvListDestination.isVisible = false
                    binding.rvAirport.isVisible = false
                    binding.tvDelete.isVisible = false
                    result.payload?.let {
                        // set list cart data
                        destinationHistoryAdapter.submitData(it)
                    }
                }
            )
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Tangani pengiriman query pencarian
                getAirportList(query)
                addToDestinationHistory(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Tangani perubahan teks pencarian
                val query = newText?.takeIf { it.isNotBlank() } // Mengambil teks jika tidak kosong
                if (query != null) {
                    getAirportList(query)
                } else {
                    showRecentSearch()
                }
                return false
            }
        })
    }

    private fun getAirportList(keyword: String? = null) {
        val token = viewModel.getToken()
        if (token != null) {
            viewModel.searchAirport(token, keyword).observe(viewLifecycleOwner) {
                it.proceedWhen(
                    doOnLoading = {
                        binding.layoutState.root.isVisible = true
                        binding.layoutState.pbLoading.isVisible = true
                        binding.layoutState.tvError.isVisible = false
                        binding.rvListDestination.isVisible = false
                        binding.rvAirport.isVisible = false
                        binding.tvRecentSearch.isVisible = false
                        binding.rvListDestination.isVisible = false
                        binding.tvDelete.isVisible = false
                    },
                    doOnSuccess = {
                        binding.layoutState.root.isVisible = false
                        binding.layoutState.pbLoading.isVisible = false
                        binding.layoutState.tvError.isVisible = false
                        binding.tvRecentSearch.isVisible = false
                        binding.rvListDestination.isVisible = false
                        binding.tvDelete.isVisible = false
                        binding.rvAirport.isVisible = true
                        it.payload?.let { data ->
                            (binding.rvAirport.adapter as? AirportListAdapter)?.submitData(data)
                        }
                    },
                    doOnEmpty = {
                        binding.layoutState.root.isVisible = true
                        binding.layoutState.pbLoading.isVisible = false
                        binding.layoutState.tvError.isVisible = true
                        binding.layoutState.tvError.text = "Lokasi Tidak Ditemukan"
                        binding.tvRecentSearch.isVisible = false
                        binding.rvListDestination.isVisible = false
                        binding.rvAirport.isVisible = false
                        binding.tvDelete.isVisible = false
                    }
                )
            }
        }
    }

    private fun showRecentSearch() {
        observeData()
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true

        // Atur tinggi menjadi 80% dari tinggi layar
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        bottomSheet.layoutParams.height = (screenHeight * 0.9).toInt()
    }

    fun setDestinationSelectionListener(listener: DestinationSelectionListener) {
        destinationSelectionListener = listener
    }

    fun addToDestinationHistory(destination:String) {
        viewModel.addToDestinationHistory(destination).observe(this) {
            it.proceedWhen(
                doOnSuccess = {

                },
                doOnError = {

                },
                doOnLoading = {

                },
            )
        }
    }

    companion object {
        private const val ARG_SOURCE = "source"

        @JvmStatic
        fun newInstance(source: String) =
            ChooseDestinationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SOURCE, source)
                }
            }
    }
}

interface DestinationSelectionListener {
    fun onDestinationSelected(airport: Airport, source: String)
}