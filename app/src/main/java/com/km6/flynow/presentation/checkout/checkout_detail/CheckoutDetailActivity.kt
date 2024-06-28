package com.km6.flynow.presentation.checkout.checkout_detail

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.km6.flynow.R

class CheckoutDetailActivity : AppCompatActivity() {
    private var btnShowPay: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_detail)
        bindView()
        btnShowPay!!.setOnClickListener { openUrlFromWebView() }
    }

    private fun bindView() {
        btnShowPay = findViewById(R.id.btn_continue_to_pay)
    }

    private fun openUrlFromWebView() {
        val intent = Intent(this, WebView::class.java)
        intent.putExtra("URL", "https://www.youtube.com/watch?v=Q7T2U1KgPBI") //ganti link sesuai BE
        startActivity(intent)
    }
}