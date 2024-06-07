package com.km6.flynow.presentation.otp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.km6.flynow.databinding.ActivityOtpBinding
import com.km6.flynow.presentation.main.MainActivity
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : AppCompatActivity() {
    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    private val viewModel: OtpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
        observeResult()
    }

    private fun setClickListeners() {
        binding.requestNewOtpEmail.setOnClickListener {
        }

        binding.pinview.setPinViewEventListener { pinview, fromUser ->
            val otp = pinview.value
            if (otp.length == 6) {
                doVerifyOtp(otp)
            }
        }
    }

    private fun doVerifyOtp(otp: String) {
        val email = intent.getStringExtra("email")
        if (email != null) {
            viewModel.verifyOtp(email, otp)
        } else {
            Toast.makeText(this, "Email is missing", Toast.LENGTH_SHORT).show()
        }
    }


    private fun observeResult() {
        viewModel.verifyOtpResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { success ->
                        navigateToMain()
                },
                doOnError = { error ->
                    Toast.makeText(
                        this,
                        "Verification Failed: ${error.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {

                }
            )
        }
    }


    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }
}
