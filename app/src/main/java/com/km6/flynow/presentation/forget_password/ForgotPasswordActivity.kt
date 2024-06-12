package com.km6.flynow.presentation.forget_password

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.km6.flynow.R
import com.km6.flynow.databinding.ActivityForgetPasswordBinding
import com.km6.flynow.databinding.ActivityLoginBinding
import com.km6.flynow.presentation.forgotpassword.ForgotPasswordViewModel
import com.km6.flynow.presentation.login.LoginActivity
import com.km6.flynow.presentation.main.MainActivity
import com.km6.flynow.presentation.register.RegisterActivity
import com.km6.flynow.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private val binding: ActivityForgetPasswordBinding by lazy {
        ActivityForgetPasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
        observeResult()
    }

    private fun observeResult() {
        viewModel.forgotPasswordResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    Toast.makeText(
                        this,
                        "Password reset link sent to your email",
                        Toast.LENGTH_SHORT,
                    ).show()
                    navigateToLogin()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    Toast.makeText(
                        this,
                        "Failed to send password reset link: ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                },
            )
        }
    }

    private fun doForgotPassword() {
        if (isFormValid()) {
            val email = binding.etEmail.text.toString().trim()
            viewModel.forgotPassword(email)
        }
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener {
            doForgotPassword()
        }

    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        return checkEmailValidation(email)
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }
}
