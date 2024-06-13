package com.km6.flynow.presentation.checkout.checkout_pemesan

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.km6.flynow.R
import com.km6.flynow.databinding.ActivityBiodataPemesanBinding

class BiodataPemesanActivity : AppCompatActivity() {
    private val binding: ActivityBiodataPemesanBinding by lazy {
        ActivityBiodataPemesanBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setHaveFamilyName()
        }

    private fun setHaveFamilyName() {
        with(binding.cvSectionDataPemesan) {
            binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    binding.tvFullName.isVisible = true
                    binding.tilFullName.isVisible = true
                    binding.tvFamilyName.isVisible = true
                    binding.tilFamilyName.isVisible = true
                    binding.tvNoTelp.isVisible = true
                    binding.tilNoTelp.isVisible = true
                    binding.tvEmail.isVisible = true
                    binding.tilEmail.isVisible = true
                } else {
                    binding.tvFullName.isVisible = true
                    binding.tilFullName.isVisible = true
                    binding.tvFamilyName.isVisible = false
                    binding.tilFamilyName.isVisible = false
                    binding.tvNoTelp.isVisible = true
                    binding.tilNoTelp.isVisible = true
                    binding.tvEmail.isVisible = true
                    binding.tilEmail.isVisible = true
                }
            }
        }
    }
}