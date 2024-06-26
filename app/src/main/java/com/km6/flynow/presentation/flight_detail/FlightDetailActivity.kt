package com.km6.flynow.presentation.flight_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.km6.flynow.R
import com.km6.flynow.databinding.ActivityFlightDetailBinding
import com.km6.flynow.presentation.checkout.checkout_pemesan.BiodataPemesanActivity
import com.km6.flynow.presentation.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FlightDetailActivity : AppCompatActivity() {
    private val binding: ActivityFlightDetailBinding by lazy {
        ActivityFlightDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnCheckout.setOnClickListener {
//            if (viewModel.isLoggedIn == null) {
//                NoLoginBottomSheet().show(supportFragmentManager, null)
//            } else {
                navigateToBiodataPemesan()
            }
        }

    private fun navigateToBiodataPemesan() {
        startActivity(
            Intent(this, BiodataPemesanActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        )
    }
}