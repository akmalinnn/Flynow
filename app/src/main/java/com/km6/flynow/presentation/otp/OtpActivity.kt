package com.km6.flynow.presentation.otp

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.GRAY
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.km6.flynow.R
import com.km6.flynow.databinding.ActivityOtpBinding
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.presentation.main.MainActivity
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : AppCompatActivity() {
    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    private val viewModel: OtpViewModel by viewModel()

    private lateinit var otpTimer: CountDownTimer
    private val otpTimerDuration: Long = 60000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        sendOtp()
        startOtpTimer()
        setClickListeners()
        observeResult()
    }

    private fun setClickListeners() {

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
        // Observing OTP verification result
        viewModel.verifyOtpResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { success ->
                    navigateToLogin()
                },
                doOnError = { error ->
                    Toast.makeText(
                        this,
                        "Verification Failed: ${error.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    // Handle loading state if needed
                }
            )
        }

        // Observing OTP resend result
        viewModel.resendOtpResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { success ->
                    Toast.makeText(this, "OTP resent successfully.", Toast.LENGTH_SHORT).show()
                },
                doOnError = { error ->
                    Toast.makeText(
                        this,
                        "Failed to resend OTP: ${error.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    // Show loading indicator when resending OTP
                    binding.requestNewOtpEmail.isEnabled = false
                }
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

//    private fun sendOtp() {
//        val email = intent.getStringExtra("email")
//        if (email != null) {
//            viewModel.resendOtp(email)
//        } else {
//            Toast.makeText(this, "Email is missing", Toast.LENGTH_SHORT).show()
//        }
//    }


    private fun startOtpTimer() {
        otpTimer = object : CountDownTimer(otpTimerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.timeOtp.text = secondsRemaining.toString()
                binding.requestNewOtpEmail.isEnabled = false
                binding.requestNewOtpEmail.setTextColor(
                    ContextCompat.getColor(binding.getRoot().context, R.color.md_theme_outline)
                )
            }

            override fun onFinish() {
                binding.timeOtp.text = "0"
                binding.requestNewOtpEmail.isEnabled = true
                binding.requestNewOtpEmail.setTextColor(
                    ContextCompat.getColor(binding.getRoot().context, R.color.md_theme_primary)
                )
                requestOtp()
            }
        }
        otpTimer.start()
    }

    private fun stopOtpTimer() {
        otpTimer.cancel()
    }

    private fun requestOtp() {
        binding.requestNewOtpEmail.setOnClickListener {
            val email = intent.getStringExtra("email")
            if (email != null) {
                viewModel.resendOtp(email)
                Toast.makeText(this, "Sending OTP", Toast.LENGTH_SHORT).show()
                startOtpTimer()
            } else {
                Toast.makeText(this, "Email is missing", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        stopOtpTimer()
    }
}
