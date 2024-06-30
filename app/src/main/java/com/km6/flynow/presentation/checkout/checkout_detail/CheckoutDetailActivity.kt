package com.km6.flynow.presentation.checkout.checkout_detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.km6.flynow.R
import com.km6.flynow.databinding.ActivityCreateBookingSuccessBinding
import com.km6.flynow.presentation.history.HistoryViewModel
import com.km6.flynow.presentation.history.historydetail.HistoryDetailActivity
import com.km6.flynow.presentation.main.MainActivity
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutDetailActivity : AppCompatActivity() {

    private val historyViewModel: HistoryViewModel by viewModel()
    private lateinit var binding: ActivityCreateBookingSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBookingSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivClose.setOnClickListener {
            onBackPressed()
        }
        binding.btnPaymentTicket.setOnClickListener {
            Toast.makeText(this, getString(R.string.membuat_pembayaran), Toast.LENGTH_SHORT).show()
            fetchLatestHistoryAndNavigate()
        }
    }

    private fun fetchLatestHistoryAndNavigate() {
        historyViewModel.getHistory().observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { response ->
                    val historyList = response.payload
                    if (!historyList.isNullOrEmpty()) {
                        val latestHistoryItem = historyList.last()
                        HistoryDetailActivity.startActivity(this, latestHistoryItem)
                        finish() // Optional: Finish CheckoutDetailActivity if you don't want to keep it in the back stack
                    }
                },
                doOnError = { error ->
                    // Handle error case, show error message or log
                },
                doOnLoading = {
                    // Handle loading state if needed
                },
                doOnEmpty = {
                    // Handle empty state if needed
                }
            )
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}
