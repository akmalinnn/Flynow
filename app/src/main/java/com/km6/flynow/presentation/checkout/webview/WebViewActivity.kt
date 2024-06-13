package com.km6.flynow.presentation.checkout.webview

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.km6.flynow.R
import com.km6.flynow.presentation.checkout.checkout_detail.CheckoutDetailActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var btnCloseSnap: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        btnCloseSnap = findViewById(R.id.btn_closeSnap)
        btnCloseSnap.setOnClickListener {
            val intent = Intent(this, CheckoutDetailActivity::class.java) //close ke home
            startActivity(intent)
        }

        val intent = intent
        val url = intent.getStringExtra("URL")
        url?.let {
            openUrlFromWebView(it)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openUrlFromWebView(url: String) {
        val webView: WebView = findViewById(R.id.myWebView)
        webView.webViewClient = object : WebViewClient() {
            private val pd: ProgressDialog = ProgressDialog(this@WebViewActivity)

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val requestUrl = request.url.toString()
                return if (requestUrl.contains("gojek://")
                    || requestUrl.contains("shopeeid://")
                    || requestUrl.contains("//wsa.wallet.airpay.co.id/")

                    // This is handle for sandbox Simulator
                    || requestUrl.contains("/gopay/partner/")
                    || requestUrl.contains("/shopeepay/")
                ) {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    startActivity(intent)
                    // `true` means for the specified url, will be handled by OS by starting Intent
                    true
                } else {
                    // `false` means any other url will be loaded normally by the WebView
                    false
                }
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                pd.setMessage("loading")
                pd.show()
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                pd.hide()
                super.onPageFinished(view, url)
            }
        }

        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl(url)
    }
}