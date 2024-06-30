package com.km6.flynow.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.km6.flynow.R
import com.km6.flynow.data.model.history.History
import com.km6.flynow.databinding.FragmentHistoryBinding
import com.km6.flynow.presentation.history.historydetail.HistoryDetailActivity
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.presentation.profile.ProfileViewModel
import com.km6.flynow.utils.GridSpacingItemDecoration
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModel()
    private val profileViewModel: ProfileViewModel by viewModel()

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
        setupSearch()
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
        val token = historyViewModel.getToken()
        if (token != null) {
            historyViewModel.getHistory().observe(viewLifecycleOwner) { it ->
                it.proceedWhen(
                    doOnLoading = {
                        binding.rvHistory.isVisible = true
                        binding.pbLoading.isVisible = true
                    },
                    doOnSuccess = {
                        binding.rvHistory.isVisible = true
                        binding.pbLoading.isVisible = false
                        it.payload?.let { data -> bindHistoryList(data) }
                    },
                    doOnError = { error ->
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.silahkan_login_kembali),
                            Toast.LENGTH_SHORT
                        ).show()
                        profileViewModel.logOut()
                        startActivity(
                            Intent(requireContext(), LoginActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            },
                        )
                    },
                    doOnEmpty = { error ->
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.anda_belum_memiliki_riwayat),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.pbLoading.isVisible = false
                    }
                )
            }
        }
    }


    private fun bindHistoryList(data: List<History>) {
        productAdapter.submitData(data)
    }

    private fun checkLoginStatus() {
        val token = historyViewModel.getToken()
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

    private fun setupSearch() {
        binding.searchContent.historySearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { productAdapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { productAdapter.filter(it) }
                return true
            }
        })
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
        binding.searchContent.historySearch.visibility = View.GONE
        binding.layoutMustLogin.imageView.visibility = View.VISIBLE
        binding.layoutMustLogin.textView2.visibility = View.VISIBLE
        binding.layoutMustLogin.btnLogin.visibility = View.VISIBLE
    }

    private fun hideMustLoginFragment() {
        binding.rvHistory.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.searchContent.historySearch.visibility = View.VISIBLE
        binding.layoutMustLogin.imageView.visibility = View.GONE
        binding.layoutMustLogin.textView2.visibility = View.GONE
        binding.layoutMustLogin.btnLogin.visibility = View.GONE
    }
}
