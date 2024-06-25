package com.km6.flynow.presentation.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.km6.flynow.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val viewModel: PaymentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        webView = findViewById(R.id.web_view_payment)
        setupWebView()
        val bookingId = intent.getIntExtra(BOOKING_ID, -1)
        val paymentAmount = intent.getIntExtra(PAYMENT_AMOUNT, -1)

        if (bookingId != -1 && paymentAmount != -1) {
            initiatePayment(bookingId, paymentAmount)
        } else {
            Toast.makeText(this, "Invalid payment details", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupWebView() {
        webView.webViewClient = WebViewClient()
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            loadsImagesAutomatically = true
        }
    }

    private fun initiatePayment(bookingId: Int, paymentAmount: Int) {
        viewModel.createPayment(bookingId, paymentAmount).observe(this, Observer { result ->
            result.fold(onSuccess = { response ->
                val paymentUrl = response.data.snapUrl
                loadPaymentUrl(paymentUrl)
            }, onFailure = { error ->
                Toast.makeText(this, "Failed to create payment: ${error.message}", Toast.LENGTH_LONG).show()
                error.printStackTrace()
            })
        })
    }

    private fun loadPaymentUrl(url: String) {
        webView.loadUrl(url)
    }


    companion object {
        private const val BOOKING_ID = "BOOKING_ID"
        private const val PAYMENT_AMOUNT = "PAYMENT_AMOUNT"

        fun startActivity(context: Context, bookingId: Int, paymentAmount: Int) {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra(BOOKING_ID, bookingId)
                putExtra(PAYMENT_AMOUNT, paymentAmount)
            }
            context.startActivity(intent)
        }
    }
}
