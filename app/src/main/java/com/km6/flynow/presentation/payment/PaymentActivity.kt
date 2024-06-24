package com.km6.flynow.presentation.payment

import android.annotation.SuppressLint
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

        val paymentUrl = intent.getStringExtra(PAYMENT_URL)
        if (paymentUrl != null) {
            loadPaymentUrl(paymentUrl)
        } else {
            Toast.makeText(this, "Payment URL not provided", Toast.LENGTH_SHORT).show()
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

    private fun loadPaymentUrl(url: String) {
        webView.loadUrl(url)
    }

    companion object {
        private const val PAYMENT_URL = "PAYMENT_URL"

        fun startActivity(context: Context, url: String) {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra(PAYMENT_URL, url)
            }
            context.startActivity(intent)
        }
    }
}
