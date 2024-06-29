package com.km6.flynow.presentation.checkout.checkout_pemesan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.km6.flynow.data.model.Booking
import com.km6.flynow.data.model.Flight
import com.km6.flynow.data.model.Search
import com.km6.flynow.databinding.ActivityBiodataPemesanBinding
import com.km6.flynow.presentation.checkout.checkout_penumpang.BiodataPenumpangActivity
import com.km6.flynow.presentation.checkout.chooseseat.SelectPassengerSeatActivity
import com.km6.flynow.presentation.home.HomeViewModel

class BiodataPemesanActivity : AppCompatActivity() {
    private val binding: ActivityBiodataPemesanBinding by lazy {
        ActivityBiodataPemesanBinding.inflate(layoutInflater)
    }

    private var searchParams: Search? = null
    private var flight: Flight? = null
    private var booking: Booking? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
        setFamilyName()
        searchParams = intent.getParcelableExtra("SEARCH_PARAMS")
        booking = intent.getParcelableExtra("BOOKING")
    }

    fun validateForm(): Boolean {
        var isValid = true

        if (binding.etFullName.text.toString().isEmpty()) {
            binding.tilFullName.error = "Full Name is required"
            isValid = false
        } else {
            binding.tilFullName.error = null
        }

        if (binding.etNoTelp.text.toString().isEmpty()) {
            binding.tilNoTelp.error = "Nomor Telepon is required"
            isValid = false
        } else {
            binding.tilNoTelp.error = null
        }

        if (binding.etEmail.text.toString().isEmpty()) {
            binding.tilEmail.error = "Email is required"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        return isValid
    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener {
           if ( validateForm()){
               navigateToBiodataPenumpang()
//               navigateToSeat()
          }
        }
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToBiodataPenumpang() {
        startActivity(
            Intent(this, BiodataPenumpangActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("SEARCH_PARAMS", searchParams)
                putExtra("BOOKING", booking)
            },
        )
    }




    private fun setFamilyName() {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            binding.tvFamilyName.isVisible = isChecked
            binding.tilFamilyName.isVisible = isChecked
            binding.etFamilyName.isVisible = isChecked
        }
    }

    companion object {
        fun startActivity(
            context: Context,

        ) {
            val intent = Intent(context, BiodataPemesanActivity::class.java)
            context.startActivity(intent)
        }
    }
}