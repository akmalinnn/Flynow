package com.km6.flynow.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.data.model.history.History
import com.km6.flynow.databinding.FragmentHistoryBinding
import com.km6.flynow.presentation.choose_destination.adapter.AirportListAdapter
import com.km6.flynow.presentation.history.historydetail.HistoryDetailActivity
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.utils.GridSpacingItemDecoration
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModel()

    private val productAdapter: HistoryAdapter by lazy {
        HistoryAdapter {
            navigateToDetailActivity(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLoginStatus()
        setupListProduct()
        getHistoryList()
    }

    private fun setupListProduct() {
        val itemDecoration = GridSpacingItemDecoration(1, 12, true)
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context).apply {
                reverseLayout = true
                stackFromEnd = true
            }
            adapter = productAdapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun getHistoryList() {
        val token = viewModel.getToken()
        if (token != null) {
            viewModel.getHistory().observe(viewLifecycleOwner) { it ->
                it.proceedWhen(
                    doOnLoading = {
                        binding.rvHistory.isVisible = true
                    },
                    doOnSuccess = {
                        binding.rvHistory.isVisible = true
                        it.payload?.let { data -> bindHistoryList(data) }
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
                            "Error Empty: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    private fun bindHistoryList(data: List<History>) {
        productAdapter.submitData(data)
    }

    private fun checkLoginStatus() {
        val token = viewModel.getToken()
        if (token == null) {
            showMustLoginFragment()
            setClickListeners()
        } else {
            hideMustLoginFragment()
        }
    }

    private fun setClickListeners() {
        binding.layoutMustLogin.btnLogin.setOnClickListener {
            startActivity(
                Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
            )
        }
    }

    private fun navigateToDetailActivity(historyItem: History) {
        val intent = Intent(requireContext(), HistoryDetailActivity::class.java).apply {
            putExtra(HistoryDetailActivity.EXTRA_HISTORY_ITEM, historyItem)
        }
        startActivity(intent)
    }

    private fun showMustLoginFragment() {
        binding.rvHistory.visibility = View.GONE
        binding.textView.visibility = View.GONE
        binding.layoutMustLogin.imageView.visibility = View.VISIBLE
        binding.layoutMustLogin.textView2.visibility = View.VISIBLE
        binding.layoutMustLogin.btnLogin.visibility = View.VISIBLE
    }

    private fun hideMustLoginFragment() {
        binding.rvHistory.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.layoutMustLogin.imageView.visibility = View.GONE
        binding.layoutMustLogin.textView2.visibility = View.GONE
        binding.layoutMustLogin.btnLogin.visibility = View.GONE
    }
}
