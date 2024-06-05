package com.km6.flynow.presentation.choose_destination

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.km6.flynow.data.model.Airport
import com.km6.flynow.data.model.Destination
import com.km6.flynow.databinding.FragmentChooseDestinationBinding
import com.km6.flynow.presentation.choose_destination.adapter.AirportListAdapter
import com.km6.flynow.presentation.choose_destination.adapter.DestinationHistoryAdapter
import com.km6.flynow.presentation.choose_destination.adapter.DestinationHistoryListener
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID


class ChooseDestinationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChooseDestinationBinding

    private val viewModel: ChooseDestinationViewModel by viewModel()

    private var destinationSelectionListener: DestinationSelectionListener? = null

    private val destinationHistoryAdapter: DestinationHistoryAdapter by lazy {
        DestinationHistoryAdapter(
            object : DestinationHistoryListener {
                override fun onRemoveDestinationClicked(destination: Destination) {
                    viewModel.removeDestination(destination)
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
        setupListAirport()
        observeData()
        setupSearchView()
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
                    binding.rvListDestination.isVisible = true
                    result.payload?.let {
                        // set list cart data
                        destinationHistoryAdapter.submitData(it)
                    }
                },
                doOnError = {
                    binding.tvDelete.text = "juju"
                },
                doOnLoading = {
                    binding.tvDelete.text = "loading"
                }
            )
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Tangani pengiriman query pencarian
                val id = UUID.randomUUID().toString()
                getAirportList(query)
                addToDestinationHistory(id, query)
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
        binding.layoutState.root.isVisible = false
        binding.layoutState.pbLoading.isVisible = false
        binding.layoutState.tvError.isVisible = false
        binding.tvRecentSearch.isVisible = true
        binding.rvListDestination.isVisible = true
        binding.rvAirport.isVisible = false
        binding.tvDelete.isVisible = true
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

    fun addToDestinationHistory(idDestination: String, destination:String) {
        viewModel.addToDestinationHistory(idDestination, destination)
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