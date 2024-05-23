package com.km6.flynow.presentation.register

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.km6.flynow.R
import com.km6.flynow.databinding.ActivityLoginBinding
import com.km6.flynow.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val binding : ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}